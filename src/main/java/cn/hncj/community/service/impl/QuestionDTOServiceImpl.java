package cn.hncj.community.service.impl;

import cn.hncj.community.bean.Question;
import cn.hncj.community.bean.User;
import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.mapper.QuestionDTOMapper;
import cn.hncj.community.mapper.QuestionMapper;
import cn.hncj.community.mapper.UserMapper;
import cn.hncj.community.service.QuestionDTOService;
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
    private UserMapper userMapper;
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

}
