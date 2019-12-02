package cn.whj.service;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.pojo.CheckItem;
import java.util.List;

/**
 * @author: 吴昊骏
 * @date: 2019/11/1 15:47
 * @description: 检查项的服务接口
 */
public interface CheckItemService {
    /**
     * @Author 吴昊骏
     * @Description 添加检查项服务
     * @Date 2019/11/1 19:16
     * @Last-Modify
     **/
    void add(CheckItem checkItem);

    /**
     * @Author 吴昊骏
     * @Description 分页查询服务
     * @Date 2019/11/1 19:17
     * @Last-Modify
     **/
    PageResult pageQuery(QueryPageBean queryPageBean);

    void edit(CheckItem checkItem);

    void delete(Integer id);

    List<CheckItem> findAll();
}
