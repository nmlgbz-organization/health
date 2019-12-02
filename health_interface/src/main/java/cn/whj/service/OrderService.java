package cn.whj.service;

import cn.whj.entity.Result;
import java.util.Map;

/**
 * @author: 吴昊骏
 * @date: 2019/11/5 10:47
 * @description:
 */
public interface OrderService {


    Result order(Map map) throws Exception;

    Map findById(int id) throws Exception;
}
