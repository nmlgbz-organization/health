package cn.whj.mapper;

import cn.whj.pojo.CheckItem;
import cn.whj.pojo.SysLog;
import com.github.pagehelper.Page;

import java.util.List;

public interface SysLogMapper {

   public void  save(SysLog sysLog);

   public List<SysLog> findall();

  public Page<SysLog> selectByCondition(String queryString);

}
