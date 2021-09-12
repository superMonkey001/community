package cn.hncj.community.controller;

import cn.hncj.community.bean.Question;
import cn.hncj.community.bean.User;
import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.mapper.QuestionMapper;
import cn.hncj.community.mapper.UserMapper;
import cn.hncj.community.service.QuestionDTOService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Wrapper;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionDTOService questionDTOService;
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        QuestionDTO question = questionDTOService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam("id") Integer id,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null||title.length()==0)
        {   model.addAttribute("error","not null");
            return "publish";
        }
        if(description==null||description.length()==0)
        {   model.addAttribute("error","text not null");
            return "publish";
        }
        if(tag==null||tag.length()==0)
        {   model.addAttribute("error","tag not null");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setId(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        UpdateWrapper<QuestionDTO> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",question.getId());
        if(question.getId()==null)
        {
            questionMapper.create(question);
        }
        else{
        questionDTOService.update(questionDTO,wrapper);
        }
        return "redirect:/";
    }
}
