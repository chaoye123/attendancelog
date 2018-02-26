package com.app.attendancelog.report;

public class ReportVo {

    private String workDay ="";//日期 2017/6/5
    private String workType = "";// 加班类型 平时
    private String beginTime = ""; // 上班时间  9:00:39
    private String overTimeBeginTime = ""; //加班开始
    private String overTimeEndTime = "";   //加班结束

    private String holidayTimeLength= "";// 节假日时长
    private String weekEndTimeLength = ""; // 周末时长（小时）
    private String overTimeTimeLength = ""; // 加班时长（小时）
    private String subsidy = "";//补贴

    public ReportVo(){
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getOverTimeBeginTime() {
        return overTimeBeginTime;
    }

    public void setOverTimeBeginTime(String overTimeBeginTime) {
        this.overTimeBeginTime = overTimeBeginTime;
    }

    public String getOverTimeEndTime() {
        return overTimeEndTime;
    }

    public void setOverTimeEndTime(String overTimeEndTime) {
        this.overTimeEndTime = overTimeEndTime;
    }

    public String getHolidayTimeLength() {
        return holidayTimeLength;
    }

    public void setHolidayTimeLength(String holidayTimeLength) {
        this.holidayTimeLength = holidayTimeLength;
    }

    public String getWeekEndTimeLength() {
        return weekEndTimeLength;
    }

    public void setWeekEndTimeLength(String weekEndTimeLength) {
        this.weekEndTimeLength = weekEndTimeLength;
    }

    public String getOverTimeTimeLength() {
        return overTimeTimeLength;
    }

    public void setOverTimeTimeLength(String overTimeTimeLength) {
        this.overTimeTimeLength = overTimeTimeLength;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }

    @Override
    public String toString() {
        return "ReportVo{" + "workDay='" + workDay + '\'' + ", workType='" + workType + '\'' + ", beginTime='" + beginTime + '\'' + ", overTimeBeginTime='" + overTimeBeginTime + '\'' + ", overTimeEndTime='" + overTimeEndTime + '\'' + ", holidayTimeLength='" + holidayTimeLength + '\'' + ", weekEndTimeLength='" + weekEndTimeLength + '\'' + ", overTimeTimeLength='" + overTimeTimeLength + '\'' + ", subsidy='" + subsidy + '\'' + '}';
    }
}
