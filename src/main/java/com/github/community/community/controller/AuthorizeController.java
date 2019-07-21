package com.github.community.community.controller;

import com.github.community.community.dto.AccessTokenDTO;
import com.github.community.community.dto.GithubUser;
import com.github.community.community.mapper.UserMapper;
import com.github.community.community.model.User;
import com.github.community.community.utils.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author TaoGe
 * @create 2019-07-20 19:22
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubUtil githubUtil;
    @Value("${github.clientId}")
    private String clientId;
    @Value("${github.client_secret}")
    private String client_secret;
    @Value("${github.uri}")
    private String uri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("callback")
    public String getCallBack(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(uri);
        accessTokenDTO.setState(state);

        String accessToken = githubUtil.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubUtil.getUser(accessToken);
        if (githubUser!=null){
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //登录成功
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else {
            //登录失败
        }
        return "/";
    }
}
