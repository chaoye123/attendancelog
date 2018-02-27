package com.app.attendancelog.web;

/**
 * Created by jacky on 2017/7/5.
 */
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.app.attendancelog.service.AttendanceLogService;

@Controller
public class SampleController {

    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    private AttendanceLogService attendanceLogService;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "http://ip/worklog/{workno}/{year}/{month}";
    }



    @RequestMapping("/worklog/{workno}/{year}/{month}")
    public void worklog(
        @PathVariable("workno") String workno,
        @PathVariable("year") Integer year,
        @PathVariable("month") Integer month, HttpServletResponse response) {


        logger.info("request params workNo:{}， year:{}, month:{}", workno, year, month);

        if(StringUtils.isBlank(workno) || !(1970<year && year <2099) || !(0<month && month<=12)){
            logger.error("输入参数有问题 workNo:{}， year:{}, month:{}", workno, year, month);
            return ;
        }

        try {
            HSSFWorkbook wk = attendanceLogService.downloadAttendanceLog(workno, year, month);
            if(wk == null){
                return;
            }

            response.addHeader("Content-Disposition", "attachment;filename=user.xls");
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            wk.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
