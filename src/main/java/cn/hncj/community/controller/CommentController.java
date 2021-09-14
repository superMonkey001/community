package cn.hncj.community.controller;

import cn.hncj.community.bean.Comment;
import cn.hncj.community.bean.User;
import cn.hncj.community.dto.CommentCreateDTO;
import cn.hncj.community.dto.CommentDTO;
import cn.hncj.community.dto.ResultDTO;
import cn.hncj.community.exception.CustomizeErrorCode;
import cn.hncj.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value="/comment",method = RequestMethod.POST)
    public ResultDTO post(@RequestBody CommentCreateDTO commentCreateDTO,
                          HttpServletRequest request
                       ){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
        {
            return ResultDTO.error(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentCreateDTO==null|| StringUtils.isBlank(commentCreateDTO.getContent()) )
        {
            return ResultDTO.error(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
//        comment.setId(1L);
        comment.setType(commentCreateDTO.getType());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
