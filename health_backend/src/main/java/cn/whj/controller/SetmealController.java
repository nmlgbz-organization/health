package cn.whj.controller;

import cn.whj.constant.MessageConstant;
import cn.whj.constant.RedisConstant;
import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.entity.Result;
import cn.whj.pojo.Setmeal;
import cn.whj.service.SetmealService;
import cn.whj.utils.QiniuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

/**
 * @author: 吴昊骏
 * @date: 2019/11/3 15:42
 * @description:
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController  {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        String fileName = null;
        try {
            String imgName = imgFile.getOriginalFilename();
            int index = imgName.lastIndexOf(".");
            String suffix = imgName.substring(index - 1);
            fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
    }

    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean pageBean){
        PageResult pageResult = setmealService.pageQuery(pageBean);
        return pageResult;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkGroupIds){
        try {
            setmealService.add(setmeal,checkGroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }


    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal,Integer[] checkGroupIds){
        try {
            setmealService.edit(setmeal,checkGroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(int id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);

        }
    }

    @RequestMapping("/findCheckGroupIds/{id}")
    public Result findCheckGroupIds(@PathVariable("id") Integer id) {
        List<Integer> list;
        try {
            list = setmealService.findCheckGroupIds(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
    }

    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id){
        try {
            setmealService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

    /**
     * 查询所有套餐信息
     * Author tlp
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        List<Setmeal> list;
        try {
            list = setmealService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, list);
    }
}
