package cn.whj.controller;

import cn.whj.constant.MessageConstant;
import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.pojo.Permission;
import cn.whj.pojo.Role;
import cn.whj.service.RoleService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author: 吴昊骏
 * @date: 2019/11/15 10:13
 * @description:123
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    /**
     * @Author 吴昊骏
     * @Description 分页条件查询
     * @Date 2019/11/14 15:40
     * @Last-Modify
     **/
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean pageBean) {
        return roleService.pageQuery(pageBean);
    }


    /**
     * @Author 吴昊骏
     * @Description 保存角色信息
     * @Date 2019/11/14 15:31
     * @Last-Modify
     **/
    @PostMapping("/save")
    public Result save(@RequestBody Role role) {

        return null;
    }

    /**
     * @Author 吴昊骏
     * @Description 修改角色信息---YL实现
     * @Date 2019/11/14 15:32
     * @Last-Modify
     **/
    @RequestMapping("/edit")
    public Result edit(@RequestBody Role role, Integer[] permissionIds) {
        try {
            Integer count = roleService.edit(role, permissionIds);
            if (count == 0) {
                return new Result(false, MessageConstant.HAS_ROLE);
            } else {
                return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
        }
    }


    /**
     * @Author 吴昊骏
     * @Description 删除角色信息，result风格
     * @Date 2019/11/14 15:39
     * @Last-Modify
     **/
    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        try {
            //id非空判断
            if (id == null) {
                return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
            }
            roleService.delete(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
    }

    //角色的新增---YL
    @RequestMapping("/add")
    public Result add(Integer[] permissionIds, @RequestBody Role role) {
        try {
            boolean flag = roleService.add(role, permissionIds);
            if (flag) {
                return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
            }
            return new Result(false, MessageConstant.HAS_ROLE);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
    }

    //查询当前登录角色的权限---YL
    @RequestMapping("/findPermission")
    public Result findPermission() {
        try {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = principal.getUsername();
            Set<Permission> list = roleService.findByUsername(username);
            return new Result(true, MessageConstant.GET_PERMISSION_SUCCESS, list);
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_PERMISSION_FAIL);
        }

    }

    //用户新建时查询所有角色---tlp
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<Role> roleList = roleService.findAll();
            return new Result(true, MessageConstant.QUERY_ROLES_SUCCESS, roleList);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_ROLES_FAIL);
        }
    }

    //编辑时查询角色的基本信息
    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id) {
        try {
            Role role = roleService.findById(id);
            return new Result(true, MessageConstant.GET_ROLE_SUCCESS, role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ROLE_FAIL);
        }
    }

    //编辑时查询角色已经存在的权限项进行回显
    @RequestMapping("/findPermissionIds")
    public Result findPermissionIds(@RequestParam("id") Integer id) {
        try {
            List<Integer> list = roleService.findPermissionIds(id);
            return new Result(true, MessageConstant.LIST_PERMISSION_IDS_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.LIST_PERMISSION_IDS_FAIL);
        }
    }
}
