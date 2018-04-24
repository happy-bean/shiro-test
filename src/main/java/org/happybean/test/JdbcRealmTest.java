package org.happybean.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author wgt
 * @date 2018-04-25
 * @description JdbcRealm
 **/
public class JdbcRealmTest {

    DruidDataSource druidDataSource = new DruidDataSource();

    {
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/shiro");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
    }

    @Test
    public void testAuthentication() {

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(druidDataSource);
        //设置查看权限
        jdbcRealm.setPermissionsLookupEnabled(true);

        //使用自定义认证sql语句
        String mysql = "select password from users where username = ?";
        jdbcRealm.setAuthenticationQuery(mysql);
        //使用自定义角色sql语句
        //dbcRealm.setUserRolesQuery(mysql);
        //使用自定义权限sql语句
        //jdbcRealm.setPermissionsQuery(mysql);

        //构建securityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
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
        subject.checkPermissions("user:delete", "user:update");
        //退出
        subject.logout();
        System.out.println(">>>>>" + subject.isAuthenticated());

    }
}
