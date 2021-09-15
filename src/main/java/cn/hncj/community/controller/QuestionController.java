package cn.hncj.community.controller;

import cn.hncj.community.dto.CommentDTO;
import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.enums.CommentTypeEnum;
import cn.hncj.community.mapper.UserMapper;
import cn.hncj.community.service.CommentService;
import cn.hncj.community.service.QuestionDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionDTOService questionDTOService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable Integer id, Model model){
        QuestionDTO questionDTO = questionDTOService.getById(id);
        List<CommentDTO> comments=commentService.listByTargetId(Long.valueOf(id), CommentTypeEnum.QUESTION);
        questionDTO.setUser(userMapper.findById(questionDTO.getCreator()));
        questionDTOService.incView(id);
        model.addAttribute("comments",comments);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
