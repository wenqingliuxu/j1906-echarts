package com.qf.j1906.service.impl;

import com.qf.j1906.po.User;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
//将Excel文档读入工程
public class ReadXLS {
    public List<User> importXLS(){
        ArrayList<User> list = new ArrayList<> ();
        try {
            String fileName = "用户表.xls";
            //创建输入流
            InputStream inputStream = new FileInputStream ("D:\\"+fileName);
            //根据输入流创建HSSFWorkBook对象
            HSSFWorkbook workbook = new HSSFWorkbook (inputStream);
            //获取HSSFWorkBook对象第一张excel表
            HSSFSheet sheetAt = workbook.getSheetAt (0);
            //遍历每一行，并使用getCell方法获取单元格
            for(Row row:sheetAt){
                //不读取表头内容
                if (row.getRowNum ()==0){
                    continue;
                }
                Integer userId =(int) row.getCell (0).getNumericCellValue ();
                String userName = row.getCell (1).getStringCellValue ();
                String userPwd = row.getCell (2).getStringCellValue ()+"";
                String userEmail = row.getCell (3).getStringCellValue ();
                Integer age=(int) row.getCell (4).getNumericCellValue ();

                User user = new User ();
                user.setUserId (userId);
                user.setName (userName);
                user.setPassword(userPwd);
                user.setGender(userEmail);
                user.setAge(age);
                list.add (user);
            }
            //关闭流
            workbook.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return list;
    }
}
