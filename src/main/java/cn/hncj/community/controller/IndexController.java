package cn.hncj.community.controller;

import cn.hncj.community.bean.User;
import cn.hncj.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Autowired
    private UserMapper mapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length>0)
        {
        for (Cookie cookie : cookies) {
            if(cookie.getName()=="token")
            {
                String token = cookie.getValue();
                User user =mapper.selectByToken(token);
                if(user!=null)
                {
                    HttpSession session = request.getSession();
                    session.setAttribute("user",user);
//                    Cookie cookie2 = new Cookie("JSESSIONID", session.getId());
//                    cookie2.setMaxAge(1);
                }
                break;
            }
        }}
        return "index";
    }
}

