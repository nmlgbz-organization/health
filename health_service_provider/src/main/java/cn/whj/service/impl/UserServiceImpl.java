package cn.whj.service.impl;

import cn.whj.constant.MessageConstant;
import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.mapper.PermissionMapper;
import cn.whj.mapper.RoleMapper;
import cn.whj.mapper.UserMapper;
import cn.whj.pojo.Permission;
import cn.whj.pojo.Role;
import cn.whj.pojo.User;
import cn.whj.service.UserService;
import cn.whj.utils.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 吴昊骏
 * @date: 2019/11/7 10:44
 * @description: 用户服务提供者
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * @Author 吴昊骏
     * @Description security自动调用该方法进行权限校验
     * @Date 2019/11/14 15:28
     * @Last-Modify
     **/
    @Override
    public User findByUsername(String s) {
        User user = userMapper.findUserByUsername(s);
        if (user == null) {
            return null;
        }
        Integer userId = user.getId();
        Set<Role> roles = roleMapper.findRoleByUserId(userId);

        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                Integer roleId = role.getId();
                Set<Permission> permissions = permissionMapper.findByRoleId(roleId);
                if (permissions != null && permissions.size() > 0) {
                    role.setPermissions(permissions);
                }

            }
        }
        user.setRoles(roles);
        return user;
    }

    /**
     * @Author 吴昊骏
     * @Description
     * @Date 2019/11/14 15:55
     * @Last-Modify
     **/
    @Override
    public PageResult pageQuery(QueryPageBean pageBean) {
        Integer currentPage = pageBean.getCurrentPage();
        Integer pageSize = pageBean.getPageSize();
        String queryString = pageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<User> userPage = userMapper.findUsersByCondition(queryString);
        long count = userPage.getTotal();
        List<User> result = userPage.getResult();
        return new PageResult(count, result);
    }

    /**
     * @Author 吴昊骏
     * @Description 保存用户信息，对密码进行加密，保存用户角色
     * @Date 2019/11/14 15:55
     * @Last-Modify
     **/
    @Override
    public Result save(User user, Integer[] checkRoleIds) {
        if (user != null) {
            String username = user.getUsername();
            List<User> userList = userMapper.findByUsername(username);
            if (userList.size() > 0) {
                return new Result(false, MessageConstant.ADD_USER_FAIL + MessageConstant.USERNAME_REPEAT);
            }
            if (user.getPassword() == null || user.getPassword() == "" || user.getPassword().length() < 4) {
                return new Result(false, MessageConstant.ADD_USER_FAIL + MessageConstant.PASSWORD_SIMPLE);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userMapper.save(user);
        if (checkRoleIds != null && checkRoleIds.length > 0) {
            for (Integer checkRoleId : checkRoleIds) {
                userMapper.saveCheckRoleId(user.getId(), checkRoleId);
            }
        }
        return new Result(true, MessageConstant.ADD_USER_SUCCESS);
    }

    @Override
    //更新用户
    public void edit(User user, Integer[] userIds) {

        int count = userMapper.findCountByUsername(user);
        if (count > 0) {
            throw new RuntimeException("该用户名已占用");
        }
        //删除用户角色关系表关系
        roleMapper.delete(user.getId());

        //更新用户表
        userMapper.update(user);

        //更新用户角色关系表
        setUserAndRole(user.getId(), userIds);

    }

    //更新关系表
    private void setUserAndRole(Integer id, Integer[] userIds) {
        Map<String, Integer> map = new HashMap<>();
        if (userIds != null && userIds.length > 0) {
            for (Integer userid : userIds) {
                map.put("user_id", id);
                map.put("role_id", userid);
                roleMapper.setUserAndRole(map);
            }
        }
    }


    /**
     * 删除用户
     *
     * @param id
     * @param username
     * @return
     */
    public Integer deleteUser(Integer id, String username) {
        //通过id查询t_user或用用户名判断时否时在删除自己--YL
        String name = userMapper.checkUser(id);
        if (username.equals(name)) {
            return 0;
        } else {
            userMapper.deleteUserRole(id);
            userMapper.deleteByUserId(id);
            return 1;
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Role> findAllRole() {
        return roleMapper.findAll();
    }


    /*分页查询*/
    public PageResult findpageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<User> page = userMapper.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public List<Integer> findRoleIdsByUid(Integer id) {
        return roleMapper.findRoleIdsByUid(id);
    }

/*    @Override
    public void addUser(User user, Integer[] roleIds) {
        userMapper.addUser(user);
        setUserAndRole(user.getId(),roleIds);
    }*/


}
