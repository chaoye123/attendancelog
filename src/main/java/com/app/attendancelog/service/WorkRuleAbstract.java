package com.app.attendancelog.service;

import org.joda.time.DateTime;
import com.app.attendancelog.dto.WorkDate;
import com.app.attendancelog.report.ReportVo;
import com.app.attendancelog.util.DateUtils;
/**
 * Created by jacky on 2017/7/12.
 */
public abstract class WorkRuleAbstract {


    public abstract ReportVo convertWorkDate2ReportVo(WorkDate workDate);

    protected String getWorkDay(DateTime dateTime){
        return dateTime.toString(DateUtils.DATE_FORMAT_YYYYMMDD);
    }

    protected DateTime getBeginTime(DateTime dateTime){
        if(dateTime == null){
            return null;
        }

        int minutes = dateTime.getMinuteOfHour();
        int hourOfDay = dateTime.getHourOfDay();

        if(minutes == 0){
            minutes = 0;
        }else if(0<minutes && minutes<30){
            minutes = 30;
        } else {
            hourOfDay += 1;
            minutes = 0;
        }
        return new DateTime(
            dateTime.getYear(),
            dateTime.getMonthOfYear(),
            dateTime.getDayOfMonth(),
            hourOfDay,
            minutes);
    }

    protected DateTime getEndTime(DateTime dateTime){
        if(dateTime == null){
            return null;
        }

        int minutes = dateTime.getMinuteOfHour();
        if(0<=minutes && minutes<30){//TODO:边界问题
            minutes = 0;

        } else {
            minutes = 30;
        }
        return new DateTime(
            dateTime.getYear(),
            dateTime.getMonthOfYear(),
            dateTime.getDayOfMonth(),
            dateTime.getHourOfDay(),
            minutes);
    }

    protected DateTime getOverTimeStartTime(DateTime dateTime, int hours){
        return new DateTime(
            dateTime.getYear(),
            dateTime.getMonthOfYear(),
            dateTime.getDayOfMonth(),
            dateTime.getHourOfDay() + hours,
            dateTime.getMinuteOfHour());
    }

}
