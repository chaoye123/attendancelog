package com.app.attendancelog.service.impl;

import org.joda.time.DateTime;
import com.app.attendancelog.dto.WorkDate;
import com.app.attendancelog.report.ReportVo;
import com.app.attendancelog.service.WorkRuleAbstract;
import com.app.attendancelog.util.DateUtils;
/**
 * Created by jacky on 2017/7/12.
 */
public class WeekdayRuleImpl extends WorkRuleAbstract {

    private static String WORKDAY_RULE = "平时";

    private static String WORKDAY_SUBSIDY = "15";

    private static int WORKDAY_TIMELENGTH = 9;

    @Override
    public ReportVo convertWorkDate2ReportVo(WorkDate workDate) {

        ReportVo reportVo = new ReportVo();
        DateTime startpoint = getBeginTime(workDate.beginTime);
        DateTime endpoint = getEndTime(workDate.endTime);

        if (startpoint == null && endpoint == null) {
            return null;
        } else if (startpoint == null || endpoint == null) {
            DateTime dateTime = startpoint != null ? startpoint : endpoint;
            reportVo.setWorkDay(getWorkDay(startpoint));
            reportVo.setWorkType(WORKDAY_RULE);
            reportVo.setBeginTime(dateTime.toString(DateUtils.DATE_FORMAT_HH_MM_SS));
            return reportVo;
        }

        reportVo.setWorkDay(getWorkDay(startpoint));
        reportVo.setWorkType(WORKDAY_RULE);

        reportVo.setBeginTime(workDate.beginTime.toString(DateUtils.DATE_FORMAT_HH_MM_SS));
        DateTime overStartPoint = getOverTimeStartTime(startpoint, WORKDAY_TIMELENGTH);
        reportVo.setOverTimeBeginTime(overStartPoint.toString(DateUtils.DATE_FORMAT_HH_MM_SS));
        reportVo.setOverTimeEndTime(workDate.endTime.toString(DateUtils.DATE_FORMAT_HH_MM_SS));

        int overTimeTimeLength = endpoint.getMinuteOfDay() - overStartPoint.getMinuteOfDay();// minute
        if (overTimeTimeLength > 0) {
            reportVo.setOverTimeTimeLength(String.valueOf(overTimeTimeLength * 1.0D / 60));
        }
        if(overTimeTimeLength >= 2*60) {// 满足两个小时才可以
            reportVo.setSubsidy(WORKDAY_SUBSIDY);
        } else {
            reportVo.setSubsidy("0");
            reportVo.setOverTimeTimeLength("0");
        }
        return reportVo;
    }
}
