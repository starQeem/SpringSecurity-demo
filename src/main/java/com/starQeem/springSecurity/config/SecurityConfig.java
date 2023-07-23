package com.starQeem.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Date: 2023/7/21 11:03
 * @author: Qeem
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private DataSource dataSource;
    @Resource
    private PersistentTokenRepository persistentTokenRepository;
    @Resource
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义登录页面,表单提交
        http.formLogin()
//                .usernameParameter("username123") //自定义入参(form表单里的)
//                .passwordParameter("password123") //自定义入参(form表单里的)
                .loginPage("/login") //自定义登录页面
                .loginProcessingUrl("/loginInput")//必须和表单的请求一样
                .successForwardUrl("/main")//登录成功后跳转的页面,post请求
                .failureForwardUrl("/error") //登录失败后跳转的页面,post请求
        ;
        //授权
        http.authorizeRequests()
                .antMatchers("/login").permitAll() //放行login,不需要认证
                .antMatchers("/error").permitAll() //放行error,不需要认证
//                .antMatchers("/css/**","/js/**","/images/**").permitAll() //放行静态资源
//                .antMatchers("/**/*.png").permitAll() //放行后缀.png
//                .regexMatchers(".+[.]png").permitAll() //放行后缀.png
//                .antMatchers("/main1").hasAnyAuthority("admin","admiN") //拥有admin或admiN权限的可以访问(严格区分大小写)
//                .antMatchers("/main1").hasRole("abc")//abc角色可以访问(严格区分大小写)
                .antMatchers("/main1").hasAnyRole("abc","ABC")//配置多个角色,abc和ABC角色可以访问(严格区分大小写)
//                .antMatchers("/main1").hasIpAddress("127.0.0.1") //指定特定ip地址可以访问
                .anyRequest().authenticated(); //所有请求都必须认证才能访问,必须登录
//        //记住我
//        http.rememberMe()
//                .tokenRepository(persistentTokenRepository)//设置数据源
////                .rememberMeParameter()
//                .tokenValiditySeconds(60) //超时时间(默认两周,这里设置为60秒)
//                .userDetailsService(userDetailsService);
        //关闭csrf防护
//        http.csrf().disable();
    }
    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        //自动建表,第一次启动时开启,第二次启动时注释掉
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}
