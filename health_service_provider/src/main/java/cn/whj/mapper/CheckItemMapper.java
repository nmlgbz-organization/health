package cn.whj.mapper;

import cn.whj.pojo.CheckItem;
import com.github.pagehelper.Page;
import java.util.List;

/**
 * @author: 吴昊骏
 * @date: 2019/11/1 16:09
 * @description: 检查项的mapper接口
 */
public interface CheckItemMapper {

    void add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(String queryString);

    void updateCheckItem(CheckItem checkItem);

    int findIdOnCheckGroupAndCheckItem(Integer id);

    void deleteById(Integer id);

    List<CheckItem> findAll();

    List<CheckItem> findById();

}
