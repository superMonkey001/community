package cn.hncj.community.controller;

import cn.hncj.community.bean.Question;
import cn.hncj.community.bean.User;
import cn.hncj.community.mapper.QuestionMapper;
import cn.hncj.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
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
        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    System.out.println(token+"====================");
                    user = userMapper.selectByToken(token);
                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
//                    Cookie cookie2 = new Cookie("JSESSIONID", session.getId());
//                    cookie2.setMaxAge(1);
                    }
                    break;
                }
            }
        }
        if (user == null) {
            model.addAttribute("error", "no login");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
