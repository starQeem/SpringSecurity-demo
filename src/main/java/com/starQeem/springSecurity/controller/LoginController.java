package com.starQeem.springSecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Date: 2023/7/21 10:51
 * @author: Qeem
 */
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/main")
    public String main(){
        return "main";
    }
    @PostMapping("/error")
    public String error(){
        return "error";
    }
//    @Secured("abc") //abc角色可以访问
//    @PreAuthorize("hasRole('abc')")  //PreAuthorize允许 ROLE_ 开头,但是配置类不允许 ROLE_ 开头
    @GetMapping("/main1")
    public String main1(){
        return "main1";
    }
    @GetMapping("/demo")
    public String demo(){
        return "demo";
    }
}
