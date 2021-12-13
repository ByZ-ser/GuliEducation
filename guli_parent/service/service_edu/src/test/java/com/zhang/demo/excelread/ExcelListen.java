package com.zhang.demo.excelread;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListen extends AnalysisEventListener<DemoData> {
   //一行一行读取表格
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("++++++++"+demoData);
    }

    //读取表头的方法
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
       System.out.println("表头:"+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
