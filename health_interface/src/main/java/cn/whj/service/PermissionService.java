package cn.whj.service;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.pojo.Permission;

import java.util.List;

public interface PermissionService {
    /**
     * 权限管理页面的分页查询--YL
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 往数据库t_permission中增加权限项--YL
     * @param permission
     */
    void addPermission(Permission permission);

    /**
     * 在编辑权限项时查询数据库信息进行回显--YL
     * @param id
     * @return
     */
    Permission findById(Integer id);

    /**
     * 修改更新权限项信息--YL
     * @param permission
     */
    void editPermission(Permission permission);

    /**
     * 删除权限项通过id--YL
     * @param permissionId
     */
    void deleteById(Integer permissionId);

    /**
     * 查询所有权限项---YL
     * @return
     */
    List<Permission> findAll();

}
