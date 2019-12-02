package cn.whj.controller;

import cn.whj.constant.MessageConstant;
import cn.whj.constant.RedisMessageConstant;
import cn.whj.entity.Result;
import cn.whj.pojo.Address;
import cn.whj.pojo.Order;
import cn.whj.service.AddressService;
import cn.whj.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author: 吴昊骏
 * @date: 2019/11/5 10:34
 * @description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Reference
    private AddressService addressService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        String  telephone = (String) map.get("telephone");
        String code = jedisPool.getResource().get( telephone+ RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        if (code == null || !code.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result = null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }

        if (result.isFlag()) {
            //预约成功发送短信
        }

        return result;
    }
    
    @RequestMapping("/findById")
    public Result findById(int id){
        try{
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    @RequestMapping("/findAllAddress")
    public Result findAllAddress(){
        try{
        List<Address> addressList=addressService.findAllAddress();
        return new Result(true,MessageConstant.QUERY_ADDRESS_SUCCESS,addressList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ADDRESS_FAIL);
        }
    }
}
