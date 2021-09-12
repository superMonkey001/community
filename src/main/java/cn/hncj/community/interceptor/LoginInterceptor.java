package cn.hncj.community.interceptor;

import cn.hncj.community.bean.User;
import cn.hncj.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        User user = null;
        if(cookies!=null&&cookies.length>0)
        {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token"))
                {
                    String token = cookie.getValue();
                    System.out.println(token+"------------------");
                    user = userMapper.selectByToken(token);
                    if(user!=null)
                    {
                        HttpSession session = request.getSession();
                        session.setAttribute("user",user);
                    }
                    break;
                }
            }}
        if (user == null){
            request.setAttribute("error","no login");
            request.getRequestDispatcher("/").forward(request,response);
            return false;
        }
        else{
            return  true;}
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
