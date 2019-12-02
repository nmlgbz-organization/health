package cn.whj.service;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderListService {

    public void cancel(Integer id);

    Result saveOrderList(Map map, Integer[] setMealIds) throws Exception;


    /**
     * 预约列表管理的分页查询YL
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

}
