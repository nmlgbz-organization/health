package cn.whj.controller;

import cn.whj.constant.MessageConstant;
import cn.whj.entity.Result;
import cn.whj.pojo.OrderSetting;
import cn.whj.service.OrderSettingService;
import cn.whj.utils.POIUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 吴昊骏
 * @date: 2019/11/4 10:18
 * @description:
 */
@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;


    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile file){
        try {
            List<String[]> list = POIUtils.readExcel(file);
            if (list != null && list.size() > 0) {
                ArrayList<OrderSetting> orderSettings = new ArrayList<>();
                for (String[] str : list) {
                    OrderSetting orderSetting = new OrderSetting(new Date(str[0]), Integer.parseInt(str[1]));
                    orderSettings.add(orderSetting);
                }
                orderSettingService.add(orderSettings);
                return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
            } else {
                return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }

    @RequestMapping("/findOrderSetting/{date}")
    public Result findOrderSetting(@PathVariable("date")String date){

        try {
            List<Map> list = orderSettingService.findOrderSetting(date);
            return new Result(true , MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
