package cn.whj.service.impl;

import cn.whj.mapper.OrderSettingMapper;
import cn.whj.pojo.OrderSetting;
import cn.whj.service.OrderSettingService;
import com.alibaba.dubbo.config.annotation.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 吴昊骏
 * @date: 2019/11/4 10:19
 * @description:
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public void add(ArrayList<OrderSetting> orderSettings) {
        if (orderSettings != null && orderSettings.size() > 0) {
            for (OrderSetting orderSetting : orderSettings) {
                int count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
                if (count > 0) {
                    orderSettingMapper.updNumByOrderDate(orderSetting);
                } else {
                    orderSettingMapper.add(orderSetting);
                }
            }

        }
    }

    @Override
    public List<Map> findOrderSetting(String date) {
        String startDate = date + "-" + 1;
        String endDate = date + "-" + 31;
        List<OrderSetting> list = orderSettingMapper.findOrderByMonth(startDate,endDate);
        ArrayList<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            HashMap map = new HashMap();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            data.add(map);
        }

        return data;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        int count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0) {
            orderSettingMapper.updNumByOrderDate(orderSetting);
        } else {
            orderSettingMapper.add(orderSetting);
        }
    }
}
