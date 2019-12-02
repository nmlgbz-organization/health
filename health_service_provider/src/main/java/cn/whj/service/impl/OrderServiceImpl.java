package cn.whj.service.impl;

import cn.whj.constant.MessageConstant;
import cn.whj.entity.Result;
import cn.whj.mapper.MemberMapper;
import cn.whj.mapper.OrderMapper;
import cn.whj.mapper.OrderSettingMapper;
import cn.whj.pojo.Member;
import cn.whj.pojo.Order;
import cn.whj.pojo.OrderSetting;
import cn.whj.service.OrderService;
import cn.whj.utils.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 吴昊骏
 * @date: 2019/11/5 10:49
 * @description:
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Result order(Map map) throws Exception {
        Date date = DateUtils.parseString2Date((String) map.get("orderDate"));

        OrderSetting orderSetting = orderSettingMapper.findSettingByDate(date);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        String telephone = (String) map.get("telephone");
        Member member = memberMapper.findByTelNum(telephone);
        if (member == null) {
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());

            memberMapper.add(member);
        }

        Integer memberId = member.getId();
        Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
        Order order = new Order(memberId, date, null, null, setmealId,null);
        List<Order> list = orderMapper.findByCondition(order);
        if (list != null && list.size() > 0) {
            return new Result(false, MessageConstant.HAS_ORDERED);
        }
        //添加地址id
        System.out.println(map.get("addressId"));
        order.setAddressId((Integer) map.get("addressId"));
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderMapper.add(order);

        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingMapper.updReservationsByDate(orderSetting);

        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
    }

    @Override
    public Map findById(int id) throws Exception {
        Map map = orderMapper.findDetailByOrderId(id);
        if (map != null) {
            Date date = (Date) map.get("orderDate");
            map.put("orderDate", DateUtils.parseDate2String(date));
        }
        return map;
    }
}
