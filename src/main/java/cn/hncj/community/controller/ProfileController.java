package cn.hncj.community.controller;

import cn.hncj.community.bean.User;
import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.mapper.QuestionDTOMapper;
import cn.hncj.community.service.QuestionDTOService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    QuestionDTOService questionDTOService;
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request, @PathVariable String action, Model model, @RequestParam(value = "pn" , defaultValue = "1") Integer pn){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null)
        {
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");}
        Page<QuestionDTO> questionDTOPage = new Page<>(pn,8);
        QueryWrapper<QuestionDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("creator",7);
        Page<QuestionDTO> myQuestions = questionDTOService.page(questionDTOPage, wrapper);
        List<QuestionDTO> records = questionDTOPage.getRecords();
        for (QuestionDTO questionDTO : records) {
            questionDTO.setUser(user);
        }
        questionDTOPage.setRecords(records);
        model.addAttribute("myQuestions",myQuestions);
        return "profile";
    }
}
