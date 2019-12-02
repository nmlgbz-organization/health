package cn.whj.service.impl;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.mapper.RoleMapper;
import cn.whj.mapper.RoleMapper;
import cn.whj.mapper.UserMapper;
import cn.whj.pojo.Permission;
import cn.whj.pojo.Role;
import cn.whj.pojo.User;
import cn.whj.service.RoleService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: 吴昊骏
 * @date: 2019/11/14 16:25
 * @description: 角色相关功能的服务提供者
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    //角色分页查询---YL
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;

    //角色分页查询---YL
    public PageResult pageQuery(QueryPageBean pageBean) {
        Integer currentPage = pageBean.getCurrentPage();
        Integer pageSize = pageBean.getPageSize();
        String queryString = pageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Role> page = roleMapper.pageQuery(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //编辑时查询角色的基本信息---YL
    public Role findById(Integer id) {
        return roleMapper.findById(id);
    }

    //编辑时查询角色已经存在的权限项进行回显---YL
    public List<Integer> findPermissionIds(Integer id) {
        return roleMapper.findPermissionIds(id);
    }

    //修改角色信息---YL
    public Integer edit(Role role, Integer[] permissionIds) {
        //判断修改的角色名称是否已经存在
        Role r = roleMapper.findByName(role.getName());
        Role r1 = roleMapper.findById(role.getId());
        if (r == null || r1.getName().equals(role.getName())) {
            //修改t_role表中角色的基本信息
            roleMapper.editRole(role);
            Integer id = role.getId();
            //删除该角色已经存在关联的permission
            roleMapper.deleteFromRolePermissionByRoleId(id);
            for (Integer permissionId : permissionIds) {
                Map<String,Integer> map = new HashMap();
                map.put("roleId",id);
                map.put("permissionId",permissionId);
                //修改t_role_permission表中角色和权限项的关联
                roleMapper.editRolePermission(map);
            }
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void save(Role role) {

    }

    /*@Override
    public void edit(Role role) {

    }*/

    /**
     * @Author 吴昊骏
     * @Description 角色删除的业务方法
     * @Date 2019/11/16 10:51
     * @Last-Modify
     **/
    @Override
    public void delete(int id) {
        //校验是否被某个用户依赖
        int count = userMapper.findCountByRoleId(id);
        if (count != 0) {
            throw new RuntimeException("该角色已被某个用户依赖，不能删除");
        } else {
            //删除角色权限中间表
            roleMapper.deleteFromRolePermissionByRoleId(id);
            roleMapper.deleteById(id);
        }
    }

    //新建角色
    @Override
    public boolean add(Role role, Integer[] permissionIds) {
        String name = role.getName();
        //保证角色名的唯一性
        Role r = roleMapper.findByName(name);
        if (r == null) {
            //不存在该角色名，新建角色
            roleMapper.save(role);
            Integer rid = role.getId();
            Map<String, Integer> map = new HashMap<>();
            if (permissionIds.length > 0 && permissionIds != null) {
                for (Integer permissionId : permissionIds) {
                    //新增角色权限中间表
                    roleMapper.insertRid4Permissionid(rid, permissionId);
                }
            }
            return true;
        } else {
            return false;
        }

    }

    //查询登录者的权限
    @Override
    public Set<Permission> findByUsername(String username) {
        System.out.println(123);
        Set<Permission> permissionSet = new HashSet<>();
        User user = userMapper.findUserByUsername(username);
        Integer id = user.getId();

        //查询登录角色的角色
        Set<Role> roleSet = roleMapper.findRoleByUserId(id);
        for (Role role : roleSet) {
            Integer roleId = role.getId();
            //查询角色拥有的权限
            System.out.println("role++" + roleId);
            List<Permission> permissions = roleMapper.findPermissionById(roleId);
            for (Permission permission : permissions) {
                //使用Set集合权限去重
                permissionSet.add(permission);
            }
        }
        return permissionSet;
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }
}






