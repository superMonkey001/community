package cn.hncj.community.service.impl;

import cn.hncj.community.bean.Comment;
import cn.hncj.community.dto.CommentDTO;
import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.enums.CommentTypeEnum;
import cn.hncj.community.exception.CustomizeErrorCode;
import cn.hncj.community.exception.CustomizeException;
import cn.hncj.community.mapper.CommentMapper;
import cn.hncj.community.mapper.QuestionDTOMapper;
import cn.hncj.community.service.CommentService;
import cn.hncj.community.service.QuestionDTOService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper,Comment > implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    QuestionDTOMapper questionDTOMapper;
    @Autowired
    private QuestionDTOService questionDTOService;
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
}
