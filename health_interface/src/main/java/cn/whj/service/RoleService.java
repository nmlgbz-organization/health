package cn.whj.service;


import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.pojo.Permission;
import cn.whj.pojo.Role;

import java.util.List;
import java.util.Set;

/**
 * @author: 吴昊骏
 * @date: 2019/11/14 16:21
 * @description:
 */
public interface RoleService {
    void save(Role role);

    void delete(int id);


    List<Role> findAll();


    boolean add(Role role, Integer[] permissionIds);

    Set<Permission> findByUsername(String username);

    //角色分页查询---YL
    PageResult pageQuery(QueryPageBean pageBean);

    //编辑时查询角色的基本信息---YL
    Role findById(Integer id);

    //编辑时查询角色已经存在的权限项进行回显---YL
    List<Integer> findPermissionIds(Integer id);

    //修改角色信息---YL
    Integer edit(Role role, Integer[] permissionIds);

}
