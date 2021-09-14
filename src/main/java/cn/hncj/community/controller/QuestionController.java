package cn.hncj.community.controller;

import cn.hncj.community.bean.User;
import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.mapper.UserMapper;
import cn.hncj.community.service.QuestionDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {
    @Autowired
    private QuestionDTOService questionDTOService;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/question/{id}")
    public String question(@PathVariable Integer id, Model model, HttpServletRequest request){
        QuestionDTO questionDTO = questionDTOService.getById(id);
        questionDTO.setUser(userMapper.findById(questionDTO.getCreator()));
        User user = (User) request.getSession().getAttribute("user");
//        System.out.println(user.getId());
        questionDTOService.incView(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
