package com.app.attendancelog.report;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelUtil<T> {

    public static HSSFWorkbook getHSSFWorkbook(ExcelVo excelVo) throws Exception {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(excelVo.getSheetName());
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        HSSFCell cell = null;
        //创建标题
        for(int i=0;i<excelVo.getTitalList().length;i++){
            cell = row.createCell(i);
            cell.setCellValue(excelVo.getTitalList()[i]);
            cell.setCellStyle(style);
        }
        //创建内容
        for(int i=0;i<excelVo.getData().size();i++){
            row = sheet.createRow(i + 1);
            List<Object> valList = reflect(excelVo.getData().get(i));
            for(int j=0;j<valList.size();j++){
                if(valList.get(j) == null){
                    row.createCell(j).setCellValue("");
                }else{
                    row.createCell(j).setCellValue(valList.get(j).toString());
                }
            }
        }
        return wb;
    }

    public static <T> List<Object> reflect(T e) throws Exception{
        Class cls = e.getClass();
        Field[] fields = cls.getDeclaredFields();
        List<Object> valList = new ArrayList();
        for(int i=0; i<fields.length; i++){
            Field f = fields[i];
            f.setAccessible(true);
            valList.add(f.get(e));
        }
        return valList;
    }

}
