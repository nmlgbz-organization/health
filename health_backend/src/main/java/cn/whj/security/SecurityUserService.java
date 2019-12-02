package cn.whj.security;

import cn.whj.pojo.Permission;
import cn.whj.pojo.Role;
import cn.whj.pojo.User;
import cn.whj.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import java.util.ArrayList;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author: 吴昊骏
 * @date: 2019/11/7 10:33
 * @description:
 */
@Component
public class SecurityUserService implements UserDetailsService  {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println(s);
        User user = userService.findByUsername(s);
        if (user == null) {
            return null;
        }
        ArrayList<SimpleGrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                list.add(new SimpleGrantedAuthority(role.getKeyword()));
                Set<Permission> permissions = role.getPermissions();
                if (permissions != null && permissions.size() > 0) {
                    for (Permission permission : permissions) {
                        list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }
        return new org.springframework.security.core.userdetails.User(s, user.getPassword(), list);
    }
}
