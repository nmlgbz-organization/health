package cn.whj.controller;

import cn.whj.constant.MessageConstant;
import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.pojo.Address;
import cn.whj.service.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lxc
 * @Program: cn.whj.controller
 * @Description:
 * @CreateTime: 11:06
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = addressService.findPage(queryPageBean);
        return pageResult;
    }

    /**
     * @Author 吴昊骏
     * @Description 地址保存的handler，接收请求封装成Address对象，
     * @Date 2019/11/16 10:39
     * @Last-Modify
     **/
    @RequestMapping("/save")
    private Result save(Address address) {
        try {
           /* System.out.println(address.getAddress());
            address.setAddress(new String(address.getAddress().getBytes("ISO-8859-1"),"UTF-8"));
            System.out.println(address.getAddress());*/
            if (address == null || "".equals(address)) {
                return new Result(false, MessageConstant.ADDRESS_IS_EMPTY);
            }
            addressService.save(address);
            return new Result(true, MessageConstant.INSERT_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
    }

    /**
     * @Author 吴昊骏
     * @Description  删除地址信息，result风格
     * @Date 2019/11/16 10:58
     * @Last-Modify 
     **/
    @RequestMapping("/delete/{id}")
    private Result delete(@PathVariable Integer id) {
        try {
            addressService.delete(id);
            return new Result(true, MessageConstant.DELETE_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping("/getAllmarker")
    public  Result getAllmarker(){
        try{
            return  new Result(true,MessageConstant.GET_MAP_SUCCESS,addressService.getAllmaker());
        }catch (Exception e){
            return  new Result(false,MessageConstant.GET_MAP_FAIL);
        }
    }
}
