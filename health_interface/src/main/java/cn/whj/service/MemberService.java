package cn.whj.service;

import cn.whj.pojo.Member;
import java.util.List;

/**
 * @author: 吴昊骏
 * @date: 2019/11/5 16:48
 * @description:
 */
public interface MemberService {

    Member phoneLogin(String telephone);

    List<Integer> findMemberCountByMonths(List<String> months);
}
