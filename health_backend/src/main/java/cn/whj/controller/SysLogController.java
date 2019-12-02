package cn.whj.controller;

import cn.whj.constant.MessageConstant;
import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.service.SysLogService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sysLog")
@RestController
public class SysLogController {

    @Reference
    private SysLogService sysLogService;

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
             return  new Result(true, MessageConstant.Get_SysLog_SUCCESS,sysLogService.findall());
        }catch (Exception e){
        return  new Result(false,MessageConstant.Get_SysLog_Fail);

        }
    }
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult=sysLogService.pageQuery(queryPageBean);
        return pageResult;
    }
}
