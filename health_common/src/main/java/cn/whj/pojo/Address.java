package cn.whj.pojo;

import java.io.Serializable;
import java.util.Date;
import org.springframework.security.core.parameters.P;

import java.io.Serializable;

/**
 * @Author: lxc
 * @Program: cn.whj.pojo
 * @Description:
 * @CreateTime: 11:24
 */
public class Address implements Serializable {
    private  Integer id;
    private  String address;//注册地址
    private  String lng;//经度
    private  String lat;//维度
    private Date regTime;//注册时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", regTime='" + regTime + '\'' +
                '}';
    }
}
