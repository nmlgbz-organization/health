package cn.whj.controller;

import cn.whj.constant.MessageConstant;
import cn.whj.entity.Result;
import cn.whj.service.MemberService;
import cn.whj.service.ReportServie;
import cn.whj.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Reference;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 吴昊骏
 * @date: 2019/11/7 14:50
 * @description:
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportServie reportServie;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusMonths(12);
        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            months.add(localDate.getYear() + "-" + localDate.getMonthValue());
            localDate = localDate.plusMonths(1);
        }

        List<Integer> memberCount = memberService.findMemberCountByMonths(months);
        HashMap<String, List> map = new HashMap<>();
        map.put("months", months);
        map.put("memberCount", memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }


    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        List<Map<String, Object>> list = setmealService.findSetmealOrder();
        Map<String, Object> data = new HashMap<>();
        data.put("setmealCount", list);

        List<String> setmealNames = new ArrayList<>();
        for (Map<String, Object> map : list) {
            String name = (String) map.get("name");
            setmealNames.add(name);
        }
        data.put("setmealNames", setmealNames);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, data);

    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> map = reportServie.getBusinessReportData();

            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = reportServie.getBusinessReportData();

            String reportDate = (String) data.get("reportDate");
            Integer todayNewMember = (Integer) data.get("todayNewMember");
            Integer totalMember = (Integer) data.get("totalMember");
            Integer thisWeekNewMember = (Integer) data.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) data.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) data.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) data.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) data.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) data.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) data.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) data.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) data.get("hotSetmeal");


            String path = request.getSession().getServletContext().getRealPath("file//template") + File.separator
                    + "report_template.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
            XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数
            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数
            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数
            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数
            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for(Map map : hotSetmeal){//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition","attachment;filename=report.xlsx");
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

}
