package cn.whj.mapper;

import cn.whj.pojo.Permission;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Set;

/**
 * @author: 吴昊骏
 * @date: 2019/11/7 10:50
 * @description:
 */
public interface PermissionMapper {
    /**
     * 根据角色id查询权限---whj
     * @param roleId
     * @return
     */
    Set<Permission> findByRoleId(Integer roleId);

    /**
     * 通过查询条件查询出所有的权限列表信息--YL
     * @param queryString
     * @return
     */
    Page<Permission> queryByCondition(String queryString);

    /**
     * 新增权限项时通过权限键词查询t_permission表判断是否已经被定义---YL
     * @param keyword
     * @return
     */
    Integer findByKeyword(String keyword);

    /**
     * 新增权限项---YL
     * @param permission
     */
    void addPermission(Permission permission);

    /**
     * 在编辑权限项时查询数据库信息进行回显---YL
     * @param id
     * @return
     */
    Permission findById(Integer id);

    /**
     * 修改更新权限项信息---YL
     * @param permission
     */
    void editPermission(Permission permission);

    /**
     * 通过id删除权限项---YL
     * @param permissionId
     */
    void delete(Integer permissionId);

    /**
     * 查询所有权限项---YL
     * @return
     */
    List<Permission> findAll();

}
