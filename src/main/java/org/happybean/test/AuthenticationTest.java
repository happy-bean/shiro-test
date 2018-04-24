package org.happybean.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wgt
 * @date 2018-04-25
 * @description 认证测试
 **/
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("admin", "123456", "admin","user");
    }

    @Test
    public void testAuthentication() {
        //构建securityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
        //登录
        subject.login(token);
        System.out.println(">>>>>" + subject.isAuthenticated());
        //检测角色
        subject.checkRole("admin");
        //检测多个角色
        subject.checkRoles("admin","user");
        //退出
        subject.logout();
        System.out.println(">>>>>" + subject.isAuthenticated());
    }
}
