package cn.whj.service.impl;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.mapper.CheckItemMapper;
import cn.whj.pojo.CheckItem;
import cn.whj.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 吴昊骏
 * @date: 2019/11/1 16:10
 * @description:
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.add(checkItem);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page =checkItemMapper.selectByCondition(queryPageBean.getQueryString());
        PageResult pageResult = new PageResult(page.getTotal(), page);
        return pageResult;
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemMapper.updateCheckItem(checkItem);
    }

    @Override
    public void delete(Integer id) {
        int count = checkItemMapper.findIdOnCheckGroupAndCheckItem(id);
        if (count > 0) {
            throw new RuntimeException("该检查项被检查组引用，不能删除");
        } else {
            checkItemMapper.deleteById(id);
        }

    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.findAll();
    }


}
