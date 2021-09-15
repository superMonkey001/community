package cn.hncj.community.service.impl;

import cn.hncj.community.bean.Comment;
import cn.hncj.community.bean.User;
import cn.hncj.community.dto.CommentDTO;
import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.enums.CommentTypeEnum;
import cn.hncj.community.exception.CustomizeErrorCode;
import cn.hncj.community.exception.CustomizeException;
import cn.hncj.community.mapper.CommentMapper;
import cn.hncj.community.mapper.QuestionDTOMapper;
import cn.hncj.community.mapper.UserMapper;
import cn.hncj.community.service.CommentService;
import cn.hncj.community.service.QuestionDTOService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper,Comment > implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    QuestionDTOMapper questionDTOMapper;
    @Autowired
    private QuestionDTOService questionDTOService;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void insert(Comment comment) {
            if (comment.getParentId()==null || comment.getParentId()==0)
            {
                throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
            }
            if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
                throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);

            }
            if(comment.getType()==CommentTypeEnum.COMMENT.getType())
            {
                Comment dbcomment = commentMapper.selectById(comment.getParentId());
                if (dbcomment==null)
                {
                    throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
                }
                commentMapper.insert(comment);
            }else
            {
                QuestionDTO questionDTO = questionDTOMapper.selectById(comment.getParentId());
//                QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("parent_id",questionDTO.getId());
//                questionDTO.setCommentCount(commentMapper.selectCount(queryWrapper));
                if (questionDTO==null)
                {
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }
                commentMapper.insert(comment);
                questionDTOService.incCommentCount(questionDTO);
            }
    }

    @Override
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id).eq("type", type.getType());
        queryWrapper.orderByDesc("gmt_create");
        List<Comment> comments = commentMapper.selectList(queryWrapper);

        if(comments.size()==0)
        {
            return new ArrayList<>();
        }
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("id", userIds);
        List<User> users = userMapper.selectList(userQueryWrapper);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOs;
    }
}
