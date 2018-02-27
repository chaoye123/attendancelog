package com.app.attendancelog.report;

import java.util.List;


public class ExcelVo<T> {
    private String sheetName;//sheet名
    private String [] titalList;//表头名列表
    private List<T> data;//数据列表

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getTitalList() {
        return titalList;
    }

    public void setTitalList(String[] titalList) {
        this.titalList = titalList;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
