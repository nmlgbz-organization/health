package cn.whj.controller;

import cn.whj.constant.MessageConstant;
import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.pojo.Permission;
import cn.whj.service.PermissionService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 操作权限管理的controller---YL
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    //分页查询权限列表展示
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean) {
        return permissionService.pageQuery(queryPageBean);
    }

    //往数据库t_permission中增加权限项
    @RequestMapping("/add")
    public Result addPermission(@RequestBody Permission permission) {
        try {
            permissionService.addPermission(permission);
            return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
        } catch (Exception e) {
            if (!e.getLocalizedMessage().equals("该权限已经被定义")) {
                e.printStackTrace();
            }
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
    }

    //编辑回显查找权限项---YL
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Permission permission = permissionService.findById(id);
            return new Result(true, MessageConstant.GET_PERMISSION_SUCCESS, permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_PERMISSION_FAIL);
        }
    }

    //编辑权限项---YL
    @RequestMapping("/editPermission")
    public Result editPermission(@RequestBody Permission permission) {
        try {
            permissionService.editPermission(permission);
            return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_PERMISSION_FAIL);
        }
    }

    //删除权限项---YL
    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer permissionId) {
        try {
            permissionService.deleteById(permissionId);
            return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }

    //查询所有权限项---YL
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<Permission> list = permissionService.findAll();
            return new Result(true,MessageConstant.LIST_ROLE_SUCCESS,list);
        }catch (Exception e){
            return new Result(false,MessageConstant.LIST_ROLE_FAIL);
        }
    }
}
