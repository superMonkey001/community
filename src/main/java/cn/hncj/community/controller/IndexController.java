package cn.hncj.community.controller;

import cn.hncj.community.bean.User;
import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.mapper.UserMapper;
import cn.hncj.community.service.QuestionDTOService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionDTOService questionDTOService;
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model, @RequestParam(value="pn",defaultValue = "1") Integer pn){
//        List<QuestionDTO> questionList=questionDTOService.list();
//        model.addAttribute("questions",questionList);
        Page<QuestionDTO> questionDTOPage = new Page<>(pn,8);
        Page<QuestionDTO> page = questionDTOService.page(questionDTOPage);
        List<QuestionDTO> questionDTOS = page.getRecords();
//        List<QuestionDTO> newQuestionDTOS = new ArrayList<>();
        for (QuestionDTO questionDTO : questionDTOS) {
            User user= userMapper.findById(questionDTO.getCreator());
            questionDTO.setUser(user);
//            newQuestionDTOS.add(questionDTO);
        }
        page.setRecords(questionDTOS);
        model.addAttribute("questions",page);
        return "index";
    }
}

