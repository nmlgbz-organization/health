package cn.whj.controller;

import cn.whj.constant.MessageConstant;
import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.pojo.CheckItem;
import cn.whj.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 吴昊骏
 * @date: 2019/11/1 15:43
 * @description: 检查项管理的控制器
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    /**
     * @Author 吴昊骏
     * @Description 订阅检查项服务
     * @Date 2019/11/1 15:49
     * @Last-Modify
     **/
    @Reference
    private CheckItemService checkItemService;


    /**
     * @Author 吴昊骏
     * @Description 新增检查项
     * @Date 2019/11/1 15:46
     * @Last-Modify
     **/
    @RequestMapping("/add")
    /*@PreAuthorize("hasAuthority('CHECKITEM_ADD')")*/
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }


    //@PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult=checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }
    
    @RequestMapping("/edit")
//    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result delete(@PathVariable("id") Integer id){
        try {
            checkItemService.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findAll")
//    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findAll(){
        try {
            List<CheckItem> list = checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
}
