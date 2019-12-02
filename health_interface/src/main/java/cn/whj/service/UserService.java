package cn.whj.service;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.pojo.Role;
import cn.whj.pojo.User;

import java.util.List;

/**
 * @author: 吴昊骏
 * @date: 2019/11/7 10:43
 * @description: 用户服务接口
 */
public interface UserService {

    User findByUsername(String s);

    PageResult pageQuery(QueryPageBean pageBean);

    Result save(User user, Integer[]checkRoleIds);

    void edit(User user,Integer[] userIds);

    /**
     * 删除用户
     * @param id
     * @param username
     */
    Integer deleteUser(Integer id, String username);
    void delete(int id);


    List<Role> findAllRole();



    PageResult findpageQuery(Integer currentPage, Integer pageSize, String queryString);

    User findById(Integer id);

    List<Integer> findRoleIdsByUid(Integer id);

   /* void addUser(User user, Integer[] roleIds);*/
}
