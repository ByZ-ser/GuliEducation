package com.zhang.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        //实现excel的写操作
        //设置写入的文件夹地址和文件名
        String filename="D:\\write.xlsx";

        //调用easyexcl方法

        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());
    }


    //创建list集合
    private static List<DemoData> getData()
    {
        List<DemoData> list=new ArrayList<>();
        for (int i=0;i<10;i++){
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("liubei"+i);
            list.add(demoData);
        }

        return list;
    }
}
