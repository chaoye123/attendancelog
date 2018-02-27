package com.app.attendancelog.service;

import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.app.attendancelog.report.ExcelUtil;
import com.app.attendancelog.report.ExcelVo;
import com.app.attendancelog.report.ReportVo;
/**
 * Created by jacky on 2017/7/5.
 */
public class HSSFReport {

    public static HSSFWorkbook queryWorkLog(List<ReportVo> data) throws Exception {
        String excelTitle[] = {
            "日期",
            "加班类型",
            "上班时间",
            "加班开始",
            "加班结束",
            "节假日（小时）",
            "周末时长（小时）",
            "加班时长（小时）",
            "补贴"
        };

        ExcelVo excelVo = new ExcelVo();
        excelVo.setSheetName("工作列表");
        excelVo.setTitalList(excelTitle);
        excelVo.setData(data);
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(excelVo);
        return wb;
    }

}
