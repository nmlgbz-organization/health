package cn.whj.service.impl;

import cn.whj.constant.RedisConstant;
import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.mapper.SetmealMapper;
import cn.whj.pojo.Setmeal;
import cn.whj.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

/**
 * @author: 吴昊骏
 * @date: 2019/11/3 15:46
 * @description:
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public PageResult pageQuery(QueryPageBean pageBean) {
        PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize());
        Page<Setmeal> pages = setmealMapper.findByCondition(pageBean.getQueryString());
        return new PageResult(pages.getTotal(), pages);
    }

    @Override
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealMapper.insSetmeal(setmeal);
        String img = setmeal.getImg();
        if (img != null) {
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);

        }
        int id = setmeal.getId();
        insTSetmealGroup(id, checkGroupIds);
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] checkGroupIds) {
        int id = setmeal.getId();
        setmealMapper.delTSetmealGroupBySid(id);
        setmealMapper.updSetmeal(setmeal);
        String img = setmeal.getImg();
        if (img != null) {
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);

        }
        insTSetmealGroup(id, checkGroupIds);
    }

    @Override
    public List<Integer> findCheckGroupIds(Integer id) {
        return setmealMapper.findCheckGroupIdsById(id);
    }

    @Override
    public void delete(Integer id) {
        setmealMapper.delTSetmealGroupBySid(id);
        Setmeal setmeal = setmealMapper.findSetmealById(id);
        String img = setmeal.getImg();
        if (img != null) {
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        }
        setmealMapper.delById(id);
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealMapper.findAll();
    }

    @Override
    public Setmeal findById(int id) {
        return setmealMapper.findSetmealById(id);
    }

    @Override
    public Setmeal findSetmealMapById(int id) {
        return setmealMapper.findSetmealMapById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealOrder() {
        return setmealMapper.findSetmealOrder();
    }

    private void insTSetmealGroup(Integer id,Integer[] checkGroupIds){
        if (checkGroupIds != null && checkGroupIds.length > 0) {
            for (Integer groupId : checkGroupIds) {
                setmealMapper.insTSetmealGroup(id, groupId);
            }
        }
    }

}
