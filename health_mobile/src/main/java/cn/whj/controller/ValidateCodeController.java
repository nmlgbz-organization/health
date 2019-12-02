package cn.whj.controller;

import cn.whj.constant.MessageConstant;
import cn.whj.constant.RedisMessageConstant;
import cn.whj.entity.Result;
import cn.whj.utils.SMSUtils;
import cn.whj.utils.ValidateCodeUtils;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author: 吴昊骏
 * @date: 2019/11/5 09:08
 * @description:
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {


    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order/{telephone}")
    public Result send4Order(@PathVariable("telephone") String telNum) {
        String validCode;
        try {
            validCode = ValidateCodeUtils.generateValidateCode(4).toString();
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telNum, validCode);

        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedisPool.getResource().setex(telNum + RedisMessageConstant.SENDTYPE_ORDER, 5 * 60, validCode);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }


    @RequestMapping("/send4Login/{telephone}")
    public Result send4Login(@PathVariable("telephone") String telNum){
        String validCode;
        try {
            validCode = ValidateCodeUtils.generateValidateCode(6).toString();
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telNum, validCode);

        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedisPool.getResource().setex(telNum + RedisMessageConstant.SENDTYPE_LOGIN, 5 * 60, validCode);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
