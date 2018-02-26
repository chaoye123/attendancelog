package com.app.attendancelog.util;

import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.Assert;
/**
 * Created by jacky on 2017/7/10.
 */
public class DateUtils {

    public static String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static String DATE_FORMAT_YYYYMMDD = "yyyy/MM/dd";
    public static String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";


    public static int getMonthDays(int year, int month) {

        Assert.isTrue(1970<year && year <= 2099, "年份输入的数值必须是1970到2099, year:" + year);
        Assert.isTrue(0<month && month <= 12, "月份输入的数值必须是1到12, month:"+month);

        Calendar c = Calendar.getInstance();
        c.set(year, month, 0);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth;
    }

    /**
     * 时间的格式 从 字符串 转换为 data
     * @param dateString
     * @return
     */
    public static Date convertString2Date(String dateString){
        // 格式验证
        Assert.isTrue(dateString.length() != DATE_FORMAT_YYYY_MM_DD_HH_MM_SS.length(), "日期格式不正确 " + dateString);
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = DateTime.parse(dateString, format);
        return dateTime.toDate();
    }

    public static DateTime convertString2DateTime(String dateString){
        // 格式验证
        Assert.isTrue(dateString.length() != DATE_FORMAT_YYYY_MM_DD_HH_MM_SS.length(), "日期格式不正确 " + dateString);
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = DateTime.parse(dateString, format);
        return dateTime;
    }
}
