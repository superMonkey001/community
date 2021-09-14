package cn.hncj.community.controller;

import cn.hncj.community.bean.User;
import cn.hncj.community.dto.AccessTokenDTO;
import cn.hncj.community.dto.GithubUser;
import cn.hncj.community.mapper.UserMapper;
import cn.hncj.community.provider.GithubProvider;
import cn.hncj.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider provider;
    @Autowired
    private UserService userService;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(String code, String state, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        AccessTokenDTO dto = new AccessTokenDTO();
        dto.setClient_id(clientId);
        dto.setClient_secret(clientSecret);
        dto.setCode(code);
        dto.setRedirect_uri(redirectUri);
        dto.setState(state);
        String token = provider.getAccessToken(dto);
        if(token==null)
        {return "redirect:/";}
        GithubUser githubUser = provider.getUser(token);
        if (githubUser != null) {
//            System.out.println(githubUser.getId());
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setAvatarUrl(githubUser.getAvatar_url());
            User updateUser = userService.createOrUpdate(user);
            session.setAttribute("user",updateUser);
            response.addCookie(new Cookie("token", user.getToken()));
            return "redirect:/";
                                }
        else {
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie token = new Cookie("token", null);
        token.setMaxAge(0);
        response.addCookie(token);
        return "redirect:/";
    }
}
