package cn.whj.mapper;

import cn.whj.pojo.CheckGroup;
import com.github.pagehelper.Page;
import java.util.List;

/**
 * @author: 吴昊骏
 * @date: 2019/11/3 10:46
 * @description:
 */
public interface CheckGroupMapper {

    public void insCheckGroup(CheckGroup checkGroup);

    public Page<CheckGroup> findByCondition(String queryString) ;

    public List<Integer> findCheckItemIdsById(Integer id) ;


    public void delTItemGroupByGid(int id);

    public void updGroup(CheckGroup checkGroup) ;

    public void delGroupById(Integer id) ;

    public void insTItemGroups(Integer id, Integer itemId) ;

    List<CheckGroup> findAll();

    List<CheckGroup> findById(int id);

    int findTSetmealGroupCountByid(Integer id);
}
