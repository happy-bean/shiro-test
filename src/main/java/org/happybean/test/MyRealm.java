package org.happybean.test;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wgt
 * @date 2018-04-25
 * @description 自定义realm
 **/
public class MyRealm extends AuthorizingRealm {

    {
        //设置realm名称
        super.setName("myRealm");
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从认证信息里获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        //通过用户名获取角色
        Set<String> roles = getRoles(username);
        //获取用户权限
        Set<String> permissions = getPermissionss(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //从认证信息里获取用户名
        String username = (String) authenticationToken.getPrincipal();
        //通过用户名获取密码
        String passwd = getPasswd(username);

        if (passwd == null) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, passwd, "myRealm");
        //加盐操作
        //simpleAuthenticationInfo.setCredentials(ByteSource.Util.bytes("salt"));
        return simpleAuthenticationInfo;
    }

    //获取用户密码
    private String getPasswd(String username) {
        return "123456";
    }

    //通过用户名获取角色
    private Set<String> getRoles(String username) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    //获取用户权限
    private Set<String> getPermissionss(String username) {
        Set<String> permissions = new HashSet<>();
        permissions.add("user:delete");
        permissions.add("user:update");
        return permissions;
    }
}
