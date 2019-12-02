package cn.whj.controller;

import cn.whj.constant.MessageConstant;
import cn.whj.constant.RedisMessageConstant;
import cn.whj.entity.Result;
import cn.whj.pojo.Member;
import cn.whj.service.MemberService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author: 吴昊骏
 * @date: 2019/11/5 16:41
 * @description:
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @RequestMapping("/phoneLogin")
    public Result phoneLogin(@RequestBody Map map, HttpServletResponse response){
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Member member = memberService.phoneLogin(telephone);

        Cookie cookie = new Cookie("login_member_phoneNum",telephone);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*30);
        response.addCookie(cookie);

        String json = JSON.toJSON(member).toString();
        jedisPool.getResource().setex(telephone, 60 * 30, json);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }

}
