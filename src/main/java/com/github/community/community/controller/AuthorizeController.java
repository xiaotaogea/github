package com.github.community.community.controller;

import com.github.community.community.dto.AccessTokenDTO;
import com.github.community.community.dto.User;
import com.github.community.community.utils.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("callback")
    public String getCallBack(@RequestParam("code") String code, @RequestParam("state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(uri);
        accessTokenDTO.setState(state);

        String accessToken = githubUtil.getAccessToken(accessTokenDTO);
        User user = githubUtil.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
