package org.happybean.spring.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wgt
 * @date 2018-04-26
 * @description
 **/
public class UserController {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(String num,String pwd){

        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token= new UsernamePasswordToken(num,pwd);
        try {
            subject.login(token);
        }catch (AuthenticationException e){
            return e.getMessage();
        }

        return "登陆成功";
    }
}
