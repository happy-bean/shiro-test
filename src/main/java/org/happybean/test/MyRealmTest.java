package org.happybean.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author wgt
 * @date 2018-04-25
 * @description 自定义realm
 **/
public class MyRealmTest {
    @Test
    public void testAuthentication() {
        MyRealm myRealm = new MyRealm();
        //构建securityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(myRealm);
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
        subject.checkRoles("admin", "user");
        //检测角色权限
        subject.checkPermission("user:delete");
        //检查角色多个权限
        subject.checkPermissions("user:delete","user:update");
        //退出
        subject.logout();
        System.out.println(">>>>>" + subject.isAuthenticated());

    }
}
