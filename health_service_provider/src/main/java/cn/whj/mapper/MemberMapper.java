package cn.whj.mapper;

import cn.whj.pojo.Member;

/**
 * @author: 吴昊骏
 * @date: 2019/11/5 12:14
 * @description:
 */
public interface MemberMapper {

    Member findByTelNum(String telephone);

    void add(Member member);

    int findCountBeforeDate(String month);

    Integer findMemberCountByDate(String today);

    Integer findMemberTotalCount();

    Integer findMemberCountAfterDate(String firstDay4Week);
}
