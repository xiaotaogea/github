package com.github.community.community.controller;

import com.github.community.community.dto.AccessTokenDTO;
import com.github.community.community.dto.User;
import com.github.community.community.utils.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("callback")
    public String getCallBack(@RequestParam("code") String code,@RequestParam("state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("97c79d18b97caf6a2ad7");
        accessTokenDTO.setClient_secret("2825e41f5f4e0153e15e5988be5a4e8f4323601f");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8081/callback");
        accessTokenDTO.setState(state);

        String accessToken = githubUtil.getAccessToken(accessTokenDTO);
        User user = githubUtil.getUser(accessToken);
        System.out.println(user.getName());
        return "hello";
    }
}
