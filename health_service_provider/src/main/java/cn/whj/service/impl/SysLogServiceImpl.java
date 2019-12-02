package cn.whj.service.impl;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.mapper.SysLogMapper;
import cn.whj.pojo.CheckItem;
import cn.whj.pojo.SysLog;
import cn.whj.service.SysLogService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = SysLogService.class)
@Transactional
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void save(SysLog sysLog) {
       sysLogMapper.save(sysLog);
    }
    @Override
    public List<SysLog> findall()  {
        return sysLogMapper.findall();

    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<SysLog> page =sysLogMapper.selectByCondition(queryPageBean.getQueryString());
        PageResult pageResult = new PageResult(page.getTotal(), page);
        return pageResult;


    }
}
