package cn.whj.mapper;

import cn.whj.pojo.User;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;


public interface UserMapper {

    User findUserByUsername(String s);

    int findCountByRoleId(int id);

    /**
     * 删除用户之前先删除用户与角色的关联信息
     * @param id
     */
//    void deleteUserRole(Integer id);

    /**
     * 根据用户Id删除用户，但不影响角色
     * @param id
     */
//    void deleteByUserId(Integer id);


    Page<User> findUsersByCondition(String queryString);

    void save(User user);

    void saveCheckRoleId(Integer id, Integer checkRoleId);

    //通过id查询出用户的姓名进行校验是否在删除子
//    String checkUser(Integer id);

    List<User> findByUsername(String username);



    //通过id删除用户---YL
//    void deleteByUserId(Integer id);

    int findCountByUsername(User user);

    void update(User user);

    Page<User> selectByCondition(String queryString);

    User findById(Integer id);

    //通过id查询出用户的姓名进行校验是否在删除自己---YL
    String checkUser(Integer id);

    //删除用户之前先删除用户与角色的关联信息---YL
    void deleteUserRole(Integer id);

    //通过id删除用户---YL
    void deleteByUserId(Integer id);
}
