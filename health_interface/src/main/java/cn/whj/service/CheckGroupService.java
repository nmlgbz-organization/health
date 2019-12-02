package cn.whj.service;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.pojo.CheckGroup;
import java.util.List;

public interface CheckGroupService {

    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    PageResult pageQuery(QueryPageBean pageBean);

    List<Integer> findCheckItemIds(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkItemIds);

    void delete(Integer id);

    List<CheckGroup> findAll();
}
