package cn.whj.service;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.pojo.Setmeal;
import java.util.List;
import java.util.Map;

public interface SetmealService {


    PageResult pageQuery(QueryPageBean pageBean);

    void add(Setmeal setmeal, Integer[] checkGroupIds);

    void edit(Setmeal setmeal, Integer[] checkGroupIds);

    List<Integer> findCheckGroupIds(Integer id);

    void delete(Integer id);

    List<Setmeal> findAll();

    Setmeal findById(int id);

    Setmeal findSetmealMapById(int id);

    List<Map<String, Object>> findSetmealOrder();
}
