package cn.whj.mapper;

import cn.whj.pojo.OrderSetting;
import java.util.Date;
import java.util.List;

public interface OrderSettingMapper {


    int findCountByOrderDate(Date orderDate);

    void updNumByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> findOrderByMonth(String startDate, String endDate);

    OrderSetting findSettingByDate(Date date);

    void updReservationsByDate(OrderSetting orderSetting);

}
