package com.zhang.demo.excelread;

import com.alibaba.excel.EasyExcel;

public class ExcelreadTest {
    public static void main(String[] args) {
        String filename="D:\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListen()).sheet().doRead();
    }
}
