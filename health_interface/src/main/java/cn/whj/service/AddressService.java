package cn.whj.service;

import cn.whj.entity.PageResult;
import cn.whj.entity.QueryPageBean;
import cn.whj.pojo.Address;
import java.util.List;

public interface AddressService {
    //分页查询地址
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * @Author 吴昊骏
     * @Description 地址保存的服务接口
     * @Date 2019/11/16 10:41
     * @Last-Modify
     **/
    void save(Address address);

    void delete(Integer id);

    List<String> getAllmaker();

    List<Address> findAllAddress();

}
