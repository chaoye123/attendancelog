package com.app.attendancelog.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.attendancelog.dto.WorkDate;
import com.app.attendancelog.report.ReportVo;
import com.app.attendancelog.util.DateUtils;
/**
 * Created by jacky on 2017/7/10.
 */
@Service
public class AttendanceLogService {

    final static Logger logger = LoggerFactory.getLogger(AttendanceLogService.class);


    @Autowired
    private WorkRuleFactory workRuleFactory;

    public HSSFWorkbook downloadAttendanceLog(String workerNo, int year, int month) {
        try {
            List<String> dateStringList = FilterWorkTime.getAllDateList(workerNo, year, month);
            List<DateTime> dataList = convertDateString2DateTime(dateStringList);
            Collections.sort(dataList, dataTimeComparator);
            List<WorkDate> workDateList = convertDateTime2WorkDate(dataList);

            // 将一天的起点和终点 通过 工作日和非工作日进行规则转换
            List<ReportVo> data = calculate(workDateList);

            // 统计 总时长等
            ReportVo total = calculateTotalWorkTime(data);
            data.add(total);

            return HSSFReport.queryWorkLog(data);

        } catch (Exception e) {
            logger.error("downloadAttendanceLog() 在计算工作日对应的数据时报错, error:{}", e.getMessage());
        }
        return null;
    }

    // overTimeTimeLength , subsidy
    private ReportVo calculateTotalWorkTime(List<ReportVo> data) {
        BigDecimal totalTime = BigDecimal.ZERO;
        BigDecimal weekendTime = BigDecimal.ZERO;
        int totalPrice = 0;
        for(ReportVo vo : data) {
            if(StringUtils.isNotBlank(vo.getOverTimeTimeLength())){
                totalTime = totalTime.add(new BigDecimal(vo.getOverTimeTimeLength()));
            }

            if(StringUtils.isNotBlank(vo.getWeekEndTimeLength())){
                weekendTime = weekendTime.add(new BigDecimal(vo.getWeekEndTimeLength()));
            }

            if(StringUtils.isNotBlank(vo.getSubsidy())){
                totalPrice += Integer.parseInt(vo.getSubsidy());
            }

            logger.info("=======>{}",vo);
        }
        ReportVo reportVo = new ReportVo();

        reportVo.setWeekEndTimeLength(weekendTime.toString());
        reportVo.setOverTimeTimeLength(totalTime.toString());
        reportVo.setSubsidy(totalPrice+"");
        logger.info("=======>{}",reportVo);

        return reportVo;
    }
    /**
     * 获取了list数据，现在进行统计
     *
     * @param workDateList
     * @return
     */
    public List<ReportVo> calculate(List<WorkDate> workDateList) {
        List<ReportVo> result = new ArrayList<>();
        WorkRuleAbstract dateRule = null;
        for (WorkDate workDate : workDateList) {
            dateRule = workRuleFactory.getDateRule(workDate.beginTime);
            ReportVo vo = dateRule.convertWorkDate2ReportVo(workDate);
            if (vo != null) {
                result.add(vo);
            }
        }
        return result;
    }

    private List<DateTime> convertDateString2DateTime(List<String> dateStringList) {
        List<DateTime> result = new ArrayList<>();
        for (String str : dateStringList) {
            result.add(DateUtils.convertString2DateTime(str));
        }
        return result;
    }

    /**
     * 核心的代码如下
     *
     * @param list
     * @return
     */
    private List<WorkDate> convertDateTime2WorkDate(List<DateTime> list) {

        List<WorkDate> result = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        } else if (list.size() == 1) {
            result.add(new WorkDate(list.get(0), null));
            return result;
        }

        // TODO: 下面的代码 是将同一天的打卡时间进行归类到WorkDate中，
        // 只需要知道每一天中打卡的最早时间和最晚时间就可以了
        List<DateTime> oneDayList = new ArrayList<>();
        DateTime first = list.get(0);
        oneDayList.add(first);
        String seachDate = first.toString(DateUtils.DATE_FORMAT_YYYY_MM_DD);
        for (int i = 1, size = list.size(); i < size; i++) {
            DateTime dt = list.get(i);
            String currentDate = dt.toString(DateUtils.DATE_FORMAT_YYYY_MM_DD);
            if (seachDate.equals(currentDate)) {
                oneDayList.add(dt);
            } else {
                result.add(convertDatetimeListToWorkDate(oneDayList));

                oneDayList = new ArrayList<>();
                oneDayList.add(dt);
                seachDate = currentDate;
            }

            if (i == size - 1 && oneDayList.size() > 0) {
                result.add(convertDatetimeListToWorkDate(oneDayList));
                oneDayList = null;
            }
        }
        return result;
    }

    private WorkDate convertDatetimeListToWorkDate(List<DateTime> list) {
        if (list.size() == 0) {
            return null;
        } else if (list.size() == 1) {
            return new WorkDate(list.get(0), null);
        }

        Collections.sort(list, dataTimeComparator);
        DateTime begin = list.get(0);
        DateTime end = list.get(list.size() - 1);
        return new WorkDate(begin, end);
    }

    private static Comparator<DateTime> dataTimeComparator = new Comparator<DateTime>() {

        @Override
        public int compare(DateTime o1, DateTime o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            return o1.compareTo(o2);
        }
    };
}
