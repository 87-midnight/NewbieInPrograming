package com.lcg.shiro.webconfigurer;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author linchuangang
 * @createTime 2020/11/4
 **/
@RestController
public class LoginController {

    @GetMapping(value = "/login")
    public String login(){
        return "require auth";
    }

    @PostMapping(value = "/unauth/login")
    public String login(@RequestBody Map<String,Object> params){
        Subject subject = SecurityUtils.getSubject();
        try {
            String username = (String) params.get("username");
            String password = (String) params.get("password");
            subject.login(new UsernamePasswordToken(username, password));
            System.out.println("登录成功!");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    @GetMapping(value = "/")
    public String index(){
        return "hello world";
    }
}
