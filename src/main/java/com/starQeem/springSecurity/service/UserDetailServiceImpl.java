package com.starQeem.springSecurity.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Date: 2023/7/21 11:05
 * @author: Qeem
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("执行自定义登录逻辑");
        //1.根据用户名去数据库查询,如果不存在抛出UsernameNotFoundException异常
        if (!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名不存在!");
        }
        //2.比对密码(注册时已经加密过),如果匹配成功,返回UserDetails
        String password = passwordEncoder.encode("1234");
        //用户名,密码,权限
        return new User(username,password, AuthorityUtils
                .commaSeparatedStringToAuthorityList("admin,normal,ROLE_abc,/main,/insert,/delete"));
    }
}
