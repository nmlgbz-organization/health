package cn.whj.service.impl;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.mapper.AddressMapper;
import cn.whj.mapper.OrderMapper;
import cn.whj.pojo.Address;
import cn.whj.service.AddressService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: lxc
 * @Program: cn.whj.service.impl
 * @Description:
 * @CreateTime: 11:09
 */
@Service(interfaceClass = AddressService.class)
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        String queryString = queryPageBean.getQueryString();
        Page<Address> rows= addressMapper.findByString(queryString);
        return new PageResult((long) rows.size(),rows);
    }

    /**
     * @Author 吴昊骏
     * @Description 保存地址的服务提供者，校验重复的地址
     * @Date 2019/11/16 10:41
     * @Last-Modify
     **/
    @Override
    public void save(Address address) {
        //查询该门店地址是否存在
        int count = addressMapper.findCountByLngAndLat(address);
        if (count != 0) {
            throw new RuntimeException("此地址已存在，请勿重复添加");
        }
        //添加门店地址的保存时间
        Date date = new Date();
        address.setRegTime(date);

        addressMapper.save(address);
    }

    /**
     * @Author 吴昊骏
     * @Description  删除地址的服务提供者
     * @Date 2019/11/16 10:59
     * @Last-Modify
     **/
    @Override
    public void delete(Integer id) {
        //校验此地址是否被预约
        int count=orderMapper.findCountByAddressId(id);
        if (count != 0) {
            throw new RuntimeException("此地址已被预约，不能删除");
        }
        addressMapper.delete(id);
    }

    @Override
    public List<String> getAllmaker() {
        return addressMapper.getAllmakers();
    }

    @Override
    public List<Address> findAllAddress() {
        return addressMapper.findAllAddress();
    }
}
