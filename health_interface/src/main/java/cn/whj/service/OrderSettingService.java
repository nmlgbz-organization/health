package cn.whj.service;

import cn.whj.pojo.OrderSetting;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void add(ArrayList<OrderSetting> orderSettings);

    List<Map> findOrderSetting(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
