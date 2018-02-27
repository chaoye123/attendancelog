package com.app.attendancelog.dto;

import org.joda.time.DateTime;
/**
 * Created by jacky on 2017/7/12.
 */
public class WorkDate {

    public DateTime beginTime;
    public DateTime endTime;

    public WorkDate(DateTime beginTime, DateTime endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
