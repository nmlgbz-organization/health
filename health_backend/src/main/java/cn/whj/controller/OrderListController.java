package cn.whj.controller;


import cn.whj.constant.MessageConstant;
import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.pojo.Order;
import cn.whj.service.OrderListService;
import cn.whj.utils.DateUtils;
import cn.whj.utils.SMSUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderList")
public class OrderListController {

    @Reference
    private OrderListService orderListService;

    //取消预约信息
    @RequestMapping("/cancel/{id}")
    public Result cancel(@PathVariable("id") Integer id) {
        try {
            orderListService.cancel(id);
            return new Result(true, MessageConstant.CANCEL_ORDER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.CANCEL_ORDER_FAIL);
        }
    }

    @RequestMapping("/orderConfirm")
    public Result orderConfirm(@RequestBody Map map) throws Exception {
        String telephone = (String) map.get("telephone");
        Date Date = DateUtils.parseString2Date((String) map.get("orderDate"));
        String orderDate = DateUtils.parseDate2String(Date);
        try {
            SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, orderDate);
            System.out.println(telephone + "," + orderDate);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, "确定预约失败");
        }
        return new Result(true, "确定预约成功");
    }

    /**
     * @param map        表单数据
     * @param setMealIds 所选套餐
     * @Author tlp
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Map map, Integer[] setMealIds) {
        try {
            return orderListService.saveOrderList(map, setMealIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ORDER_LIST_FAIL);
        }
    }

    //预约列表管理的分页查询YL
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean) {
        return orderListService.pageQuery(queryPageBean);
    }
}
