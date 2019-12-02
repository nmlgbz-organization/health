package cn.whj.jobs;

import cn.whj.constant.RedisConstant;
import cn.whj.utils.QiniuUtils;
import java.time.LocalDate;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author: 吴昊骏
 * @date: 2019/11/3 19:49
 * @description:
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        Jedis jedis = jedisPool.getResource();
        Set<String> set = jedis.sdiff(RedisConstant.SETMEAL_PIC_DB_RESOURCES, RedisConstant.SETMEAL_PIC_RESOURCES);
        if (set != null) {
            for (String s : set) {
                System.out.println(s);
                QiniuUtils.deleteFileFromQiniu(s);
                jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES, s);
                System.out.println("执行一次");
            }
        }


    }



}
