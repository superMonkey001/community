package cn.hncj.community.controller;

import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.service.QuestionDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionDTOService questionDTOService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable Integer id, Model model){
        QuestionDTO questionDTO = questionDTOService.getById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
