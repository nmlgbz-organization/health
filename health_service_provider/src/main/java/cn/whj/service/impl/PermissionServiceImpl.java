package cn.whj.service.impl;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.mapper.PermissionMapper;
import cn.whj.pojo.Permission;
import cn.whj.service.PermissionService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    //权限管理页面的分页查询--YL
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Permission> page = permissionMapper.queryByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //往数据库t_permission中增加权限项
    public void addPermission(Permission permission) {
        String keyword = permission.getKeyword();
        Integer count = permissionMapper.findByKeyword(keyword);
        if (count > 0) {
            throw new RuntimeException("该权限已经被定义");
        } else {
            permissionMapper.addPermission(permission);
        }
    }

    //在编辑权限项时查询数据库信息进行回显
    public Permission findById(Integer id) {
        return permissionMapper.findById(id);
    }

    //修改更新权限项信息
    public void editPermission(Permission permission) {
        permissionMapper.editPermission(permission);
    }

    //删除权限项通过id
    public void deleteById(Integer permissionId) {
        permissionMapper.delete(permissionId);
    }

    //查询所有权限项---YL
    public List<Permission> findAll() {
        return permissionMapper.findAll();
    }
}
