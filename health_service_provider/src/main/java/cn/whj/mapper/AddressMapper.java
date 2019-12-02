package cn.whj.mapper;

import cn.whj.pojo.Address;
import com.github.pagehelper.Page;

import java.util.List;

public interface AddressMapper {

    Page<Address> findByString(String queryString);

    int findCountByLngAndLat(Address address);

    void save(Address address);

    void delete(Integer id);

    List<String> getAllmakers();

    List<Address> findAllAddress();

}
