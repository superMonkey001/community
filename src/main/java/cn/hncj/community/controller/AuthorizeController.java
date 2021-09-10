package cn.hncj.community.controller;

import cn.hncj.community.dto.AccessTokenDTO;
import cn.hncj.community.dto.GithubUser;
import cn.hncj.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider provider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(String code, String state, HttpSession session) {
        AccessTokenDTO dto = new AccessTokenDTO();
        dto.setClient_id(clientId);
        dto.setClient_secret(clientSecret);
        dto.setCode(code);
        dto.setRedirect_uri(redirectUri);
        dto.setState(state);
        String token = provider.getAccessToken(dto);
        GithubUser user = provider.getUser(token);
        if(user!=null)
        {
            session.setAttribute("user",user);
            return "redirect:/";
        }else
        {
            return "redirect:/";
        }
    }
}
