package cn.hncj.community.service.impl;

import cn.hncj.community.bean.Comment;
import cn.hncj.community.bean.Question;
import cn.hncj.community.bean.User;
import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.exception.CustomizeErrorCode;
import cn.hncj.community.exception.CustomizeException;
import cn.hncj.community.mapper.CommentMapper;
import cn.hncj.community.mapper.QuestionDTOMapper;
import cn.hncj.community.mapper.QuestionMapper;
import cn.hncj.community.mapper.UserMapper;
import cn.hncj.community.service.QuestionDTOService;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionDTOServiceImpl extends ServiceImpl<QuestionDTOMapper,QuestionDTO> implements QuestionDTOService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionDTOMapper questionDTOMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    public List<QuestionDTO> list() {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> list = questionMapper.list();
        for (Question question : list) {
            User user= userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

    public void createOrUpdate(QuestionDTO questionDTO) {
        if (questionDTO.getId() == null) {
            // 创建
            questionDTO.setGmtCreate(System.currentTimeMillis());
            questionDTO.setGmtModified(questionDTO.getGmtCreate());
            questionDTO.setViewCount(0);
            questionDTO.setLikeCount(0);
            questionDTO.setCommentCount(0);
            questionDTOMapper.insert(questionDTO);
        } else {
            // 更新
            QuestionDTO updateQuestionDTO = new QuestionDTO();
            updateQuestionDTO.setGmtModified(questionDTO.getGmtCreate());
            updateQuestionDTO.setTitle(questionDTO.getTitle());
            updateQuestionDTO.setDescription(questionDTO.getDescription());
            updateQuestionDTO.setTag(questionDTO.getTag());
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("parent_id",questionDTO.getId());
            updateQuestionDTO.setCommentCount(commentMapper.selectCount(commentQueryWrapper));
            UpdateWrapper<QuestionDTO> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",questionDTO.getId());
            int updated = questionDTOMapper.update(questionDTO, updateWrapper);
            if(updated!=1)
            {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    @Override
    public void incView(Integer id) {
        QuestionDTO questionDTO = questionDTOMapper.selectById(id);
        QuestionDTO updateQuestionDTO = new QuestionDTO();
        questionDTO.setViewCount(questionDTO.getViewCount()+1);
        BeanUtils.copyProperties(questionDTO,updateQuestionDTO);
        UpdateWrapper<QuestionDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        questionDTOMapper.update(updateQuestionDTO,updateWrapper);
    }

    @Override
    public void incCommentCount(QuestionDTO questionDTO) {
        QuestionDTO updateQuestionDTO = new QuestionDTO();
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",questionDTO.getId());
        questionDTO.setCommentCount(commentMapper.selectCount(queryWrapper));
        BeanUtils.copyProperties(questionDTO,updateQuestionDTO);
        UpdateWrapper<QuestionDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",questionDTO.getId());
        questionDTOMapper.update(updateQuestionDTO,updateWrapper);
    }

}
