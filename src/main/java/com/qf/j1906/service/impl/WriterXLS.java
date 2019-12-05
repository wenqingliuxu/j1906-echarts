package com.qf.j1906.service.impl;

import com.qf.j1906.po.User;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
//导出Excel数据
public class WriterXLS {

    public void exportExcel(List<User> list) {
        // 创建HSSFWorkBook对象 （Excel文档对象）
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook ();
        //创建excel表
        HSSFSheet sheet = hssfWorkbook.createSheet ();
        //在当前表中创建行，第一行也就是rowNum为0的行一般为表头行
        HSSFRow titleRow = sheet.createRow (0);
        //编辑表头
        // 给当前行titleRow创建单元格，并使用setCellValue方法赋值
        titleRow.createCell (0).setCellValue ("用户编号");
        titleRow.createCell (1).setCellValue ("用户姓名");
        titleRow.createCell (2).setCellValue ("用户密码");
        titleRow.createCell (3).setCellValue ("用户性别");
        titleRow.createCell (4).setCellValue ("用户年龄");
        for (User user : list) {
            //获取当前最大的行
            int lastRowNum = sheet.getLastRowNum ();
            //新创建一行操作
            HSSFRow dataRow = sheet.createRow (lastRowNum + 1);
            dataRow.createCell (0).setCellValue (user.getUserId ());
            dataRow.createCell (1).setCellValue (user.getName ()==null?"":user.getName ());
            dataRow.createCell (2).setCellValue (user.getPassword()==null?"":user.getPassword ());
            dataRow.createCell (3).setCellValue (user.getGender ()==null?"":user.getGender ());
            dataRow.createCell (4).setCellValue(user.getAge());

        //    String.valueOf(df.format(cell.getNumericCellValue()))
        }
        String fileName = "用户表.xls";
        try {
            //创建输出流及本地存储地址
            FileOutputStream fileOut = new FileOutputStream ("D:\\" + fileName);
            //导出数据到本地，并关闭流
            hssfWorkbook.write (fileOut);
            hssfWorkbook.close ();
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
}
