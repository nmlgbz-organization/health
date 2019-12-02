package cn.whj.service.impl;

import cn.whj.constant.MessageConstant;
import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.mapper.MemberMapper;
import cn.whj.mapper.OrderListMapper;
import cn.whj.mapper.OrderMapper;
import cn.whj.mapper.OrderSettingMapper;
import cn.whj.pojo.Member;
import cn.whj.pojo.Order;
import cn.whj.pojo.OrderSetting;
import cn.whj.service.OrderListService;
import cn.whj.utils.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderListService.class)
@Transactional
public class OrderListServiceImpl implements OrderListService{

    @Autowired
    private OrderListMapper orderListMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public void cancel(Integer id) {

        orderListMapper.cancel(id);
    }

    /**
     * 新增预约数据
     * @param map        表单数据
     * @param setMealIds 所选套餐ID数组
     * @Author tlp
     */
    @Override
    public Result saveOrderList(Map map, Integer[] setMealIds) throws Exception {
        Date orderDate = DateUtils.parseString2Date(map.get("orderDate").toString());
        OrderSetting orderSetting = orderSettingMapper.findSettingByDate(orderDate);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        Member member = memberMapper.findByTelNum((String) map.get("phoneNumber"));
        if (member == null) {
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber((String) map.get("phoneNumber"));
            member.setRegTime(new Date());
            member.setBirthday(DateUtils.parseString2Date((String) map.get("orderDate")));
            memberMapper.add(member);
        }
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setOrderType(Order.ORDERTYPE_TELEPHONE);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        if (setMealIds != null && setMealIds.length > 0) {
            for (Integer setMealId : setMealIds) {
                order.setSetmealId(setMealId);
                List<Order> list = orderMapper.findByCondition(order);
                if (list != null && list.size() > 0) {
                    return new Result(false, MessageConstant.HAS_ORDERED);
                }
                orderMapper.add(order);
                orderSetting.setReservations(orderSetting.getReservations() + 1);
                orderSettingMapper.updReservationsByDate(orderSetting);
            }
        }
        return new Result(true, MessageConstant.ADD_ORDER_LIST_SUCCESS, order.getId());
    }

    //预约列表管理的分页查询---YL
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Order> page = orderMapper.findOrderList(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
