package cn.whj.service;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.pojo.CheckItem;
import cn.whj.pojo.SysLog;
import com.github.pagehelper.Page;

import java.util.List;

public interface SysLogService {

   public void  save(SysLog sysLog);

   public List<SysLog> findall();

   PageResult pageQuery(QueryPageBean queryPageBean);

}
