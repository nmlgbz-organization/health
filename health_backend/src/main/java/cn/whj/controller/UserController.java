package cn.whj.controller;

import cn.whj.pojo.User;
import cn.whj.constant.MessageConstant;
import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.pojo.Role;
import cn.whj.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 吴昊骏
 * @date: 2019/11/7 13:51
 * @description: 用户
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;


    /**
     * @Author 吴昊骏
     * @Description 从security框架中拿用户名
     * @Date 2019/11/14 15:30
     * @Last-Modify
     **/
    @RequestMapping("/getUsername")
    public Result getUsername() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    /**
     * @Author 吴昊骏
     * @Description 分页条件查询
     * @Date 2019/11/14 15:40
     * @Last-Modify
     **/
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean pageBean) {
        try {
            PageResult pageResult = userService.pageQuery(pageBean);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @Author 吴昊骏
     * @Description 保存用户信息
     * @Date 2019/11/14 15:31
     * @Last-Modify
     **/
    @PostMapping("/save")
    public Result save(@RequestBody cn.whj.pojo.User user, Integer[] checkItemIds) {
        try {
            return userService.save(user, checkItemIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }

    /**
     * @Author 吴昊骏
     * @Description 修改用户信息
     * @Date 2019/11/14 15:32
     * @Last-Modify
     **/
    @RequestMapping("/edit")
    public Result edit(@RequestBody cn.whj.pojo.User user) {

        return null;
    }


   //删除用户
    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        try {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            Integer i = userService.deleteUser(id, username);
            if (i == 0) {
                //i=0代表删除请求自己
                return new Result(false, MessageConstant.DELETE_USER_ERROR);
            } else {
                //i=1代表，删除成功
                return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
    }


    @RequestMapping("/findAllRole")
    /*@PreAuthorize("hasAuthority('ROLE_QUERY')")*/
    public Result findAllRole() {
        try {
            List<Role> roleList = userService.findAllRole();
            return new Result(true, "查询角色成功", roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询角色失败");
        }
    }

    /*编辑用户*/
    /* @PreAuthorize("hasAuthority('USER_EDIT')")*/
    @RequestMapping("/Edit")
    public Result Edit(@RequestBody User user, Integer[] roleIds) {
        try {
            userService.edit(user, roleIds);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "编辑用户失败");
        }
        return new Result(true, "编辑用户成功");
    }

    /* *//*新建用户*//*
    @RequestMapping("/addUser")
    @PreAuthorize("hasAuthority('USER_ADD')")
    public Result add(@RequestBody User user,Integer[] roleIds){
        try{
            userService.addUser(user,roleIds);
        }catch (Exception e){
            return new Result(false,"添加用户失败");
        }
        return new Result(true,"添加用户成功");
    }*/


    //分页查询
    @RequestMapping("/findpage")
    @PreAuthorize("hasAuthority('USER_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = userService.findpageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }

    @RequestMapping("/findById")
    /*@PreAuthorize("hasAuthority('USER_QUERY')")*/
    public Result findById(Integer id) {

        try {
            User user = userService.findById(id);
            return new Result(true, "根据id查询用户成功", user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "根据用户信息查询失败");
        }

    }

    //根据用户id查询角色id
    @RequestMapping("/findRoleIdsByUid")
    public Result findRoleIdsByUid(Integer id) {
        try {
            List<Integer> roleIds = userService.findRoleIdsByUid(id);
            return new Result(true, "查询角色id成功", roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询角色id失败");
        }
    }


}
