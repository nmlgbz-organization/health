package cn.whj.mapper;

import cn.whj.pojo.Setmeal;
import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;

/**
 * @author: 吴昊骏
 * @date: 2019/11/3 15:46
 * @description:
 */
public interface SetmealMapper {

    Page<Setmeal> findByCondition(String queryString);

    void insSetmeal(Setmeal setmeal);

    void insTSetmealGroup(int id, Integer groupId);

    void delTSetmealGroupBySid(int id);

    void updSetmeal(Setmeal setmeal);

    List<Integer> findCheckGroupIdsById(int id);

    void delById(Integer id);

    Setmeal findSetmealById(int id);

    List<Setmeal> findAll();

    Setmeal findSetmealMapById(int id);

    List<Map<String, Object>> findSetmealOrder();
}
