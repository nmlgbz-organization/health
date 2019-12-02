package cn.whj.service.impl;

import cn.whj.mapper.MemberMapper;
import cn.whj.mapper.OrderMapper;
import cn.whj.service.ReportServie;
import cn.whj.utils.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 吴昊骏
 * @date: 2019/11/8 09:47
 * @description:
 */
@Service(interfaceClass = ReportServie.class)
@Transactional
public class ReportServieImpl implements ReportServie {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 获得运营统计数据 Map数据格式： todayNewMember ‐> number
     * totalMember ‐> number
     * thisWeekNewMember ‐> number
     * thisMonthNewMember‐> number
     * todayOrderNumber ‐> number
     * todayVisitsNumber ‐> number
     * thisWeekOrderNumber ‐> number
     * thisWeekVisitsNumber ‐> number
     * thisMonthOrderNumber ‐> number
     * thisMonthVisitsNumber ‐> number
     * hotSetmeals ‐> List<Setmeal>
     */
    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        String firstDay4Week = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String firstDay4Month = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        Integer todayNewMember = memberMapper.findMemberCountByDate(today);

        Integer totalMember = memberMapper.findMemberTotalCount();

        Integer thisWeekNewMember = memberMapper.findMemberCountAfterDate(firstDay4Week);

        Integer thisMonthNewMember = memberMapper.findMemberCountAfterDate(firstDay4Month);

        Integer todayOrderNumber = orderMapper.findOrderCountByDate(today);

        Integer thisWeekOrderNumber = orderMapper.findOrderCountAfterDate(firstDay4Week);

        Integer thisMonthOrderNumber = orderMapper.findOrderCountAfterDate(firstDay4Month);

        Integer todayVisitsNumber = orderMapper.findVisitsCountByDate(today);

        Integer thisWeekVisitsNumber = orderMapper.findVisitsCountAfterDate(firstDay4Week);

        Integer thisMonthVisitsNumber = orderMapper.findVisitsCountAfterDate(firstDay4Month);

        List<Map> hotSetmeal = orderMapper.findHotSetmeal();
        Map<String, Object> result = new HashMap<>();
        result.put("reportDate", today);
        result.put("todayNewMember", todayNewMember);
        result.put("totalMember", totalMember);
        result.put("thisWeekNewMember", thisWeekNewMember);
        result.put("thisMonthNewMember", thisMonthNewMember);
        result.put("todayOrderNumber", todayOrderNumber);
        result.put("thisWeekOrderNumber", thisWeekOrderNumber);
        result.put("thisMonthOrderNumber", thisMonthOrderNumber);
        result.put("todayVisitsNumber", todayVisitsNumber);
        result.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        result.put("hotSetmeal", hotSetmeal);
        return result;


    }
}
