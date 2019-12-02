package cn.whj.service.impl;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.mapper.CheckGroupMapper;
import cn.whj.pojo.CheckGroup;
import cn.whj.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 吴昊骏
 * @date: 2019/11/3 10:46
 * @description:
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupMapper.insCheckGroup(checkGroup);
        Integer id = checkGroup.getId();
        insTItemGroups(id, checkItemIds);
    }

    @Override
    public PageResult pageQuery(QueryPageBean pageBean) {
        PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize());
        Page<CheckGroup> pages = checkGroupMapper.findByCondition(pageBean.getQueryString());
        return new PageResult(pages.getTotal(), pages);
    }

    @Override
    public List<Integer> findCheckItemIds(Integer id) {
        return checkGroupMapper.findCheckItemIdsById(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        int id = checkGroup.getId();
        checkGroupMapper.delTItemGroupByGid(id);
        checkGroupMapper.updGroup(checkGroup);
        insTItemGroups(id, checkItemIds);
    }

    @Override
    public void delete(Integer id) {
        int count = checkGroupMapper.findTSetmealGroupCountByid(id);
        if (count > 0) {
            throw new RuntimeException("此检查组已被某个套餐引用，无法删除");
        } else {
            checkGroupMapper.delTItemGroupByGid(id);
            checkGroupMapper.delGroupById(id);
        }

    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupMapper.findAll();
    }

    private void insTItemGroups(Integer id, Integer[] checkItemIds) {
        if (checkItemIds != null && checkItemIds.length > 0) {
            for (Integer itemId : checkItemIds) {
                checkGroupMapper.insTItemGroups(id, itemId);
            }
        }
    }

}
