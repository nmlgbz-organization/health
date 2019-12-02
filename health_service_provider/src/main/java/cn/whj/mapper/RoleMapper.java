package cn.whj.mapper;

import cn.whj.pojo.Permission;
import cn.whj.pojo.Role;

import java.util.List;
import java.util.Map;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Set;

/**
 * @author: 吴昊骏
 * @date: 2019/11/7 10:49
 * @description:
 */
public interface RoleMapper {

    Set<Role> findRoleByUserId(Integer userId);

    List<Role> findAll();

    void insertRid4Permissionid(Integer rid, Integer permissionId);

    void save(Role role);

    List<Permission> findPermissionById(Integer id);

    void deleteById(int id);

    //角色分页查询---YL
    Page<Role> pageQuery(String queryString);

    //查询是否有该角色---YL
    Role findByName(String name);

    //角色编辑时查询角色的基本信息---YL
    Role findById(Integer id);
   /* //角色编辑时查询角色的基本信息---YL
    Role findById(Integer id);
*/
    //角色编辑时查询角色已经存在的权限项进行回显---YL
    List<Integer> findPermissionIds(Integer id);

    /*//角色编辑时查询角色已经存在的权限项进行回显---YL
    List<Integer> findPermissionIds(Integer id);*/
    //修改t_role表中角色的基本信息---YL
    void editRole(Role role);

    //删除该角色已经存在关联的permission
    void deleteFromRolePermissionByRoleId(Integer id);

    //修改t_role_permission表中角色和权限项的关联---YL
    void editRolePermission(Map<String, Integer> map);

    /*//修改t_role表中角色的基本信息---YL
    void editRole(Role role);*/

    /*//删除该角色已经存在关联的permission
    void deleteFromRolePermissionByRoleId(Integer id);*/

    //修改t_role_permission表中角色和权限项的关联---YL
    /*void editRolePermission(Map<String, Integer> map);*/
    List<Integer> findRoleIdsByUid(Integer id);

    void deleteRolePermissionByRoleId(int id);

    void delete(Integer id);

    void setUserAndRole(Map<String, Integer> map);
}
