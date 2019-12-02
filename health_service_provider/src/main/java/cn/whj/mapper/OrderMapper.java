package cn.whj.mapper;

import cn.whj.pojo.Order;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author: 吴昊骏
 * @date: 2019/11/5 10:49
 * @description:
 */
public interface OrderMapper {

    List<Order> findByCondition(Order order);

    void add(Order order);

    Map findDetailByOrderId(int id);

    Integer findOrderCountByDate(String today);

    Integer findOrderCountAfterDate(String firstDay4Week);

    Integer findVisitsCountByDate(String today);

    Integer findVisitsCountAfterDate(String firstDay4Week);

    List<Map> findHotSetmeal();

    int findCountByAddressId(Integer id);

    //查询所有的预约订单以及相关的会员信息以及地址信息--YL
    Page<Order> findOrderList(String queryString);
}
