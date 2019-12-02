package cn.whj.service.impl;

import cn.whj.mapper.MemberMapper;
import cn.whj.pojo.Member;
import cn.whj.service.MemberService;
import com.alibaba.dubbo.config.annotation.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 吴昊骏
 * @date: 2019/11/5 16:48
 * @description:
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member phoneLogin(String telephone) {
        Member member = memberMapper.findByTelNum(telephone);
        if (member == null) {
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberMapper.add(member);
        }
        return member;
    }

    @Override
    public List<Integer> findMemberCountByMonths(List<String> months) {
        ArrayList<Integer> list = new ArrayList<>();
        for (String month : months) {
            month = month + "-31";
            int count=memberMapper.findCountBeforeDate(month);
            list.add(count);
        }
        return list;
    }
}
