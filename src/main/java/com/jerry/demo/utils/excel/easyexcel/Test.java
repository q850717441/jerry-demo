package com.jerry.demo.utils.excel.easyexcel;

import com.alibaba.excel.metadata.Sheet;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 测试类
 * @author: chenmingjian
 * @date: 19-4-4 15:24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    /**
     * 读取少于1000行的excle
     */
    @org.junit.Test
    public void readLessThan1000Row(){
        String filePath = "/Users/jerry/Downloads/测试.xlsx";
        List<Object> objects = ExcelUtil.readLessThan1000Row(filePath);
        objects.forEach(System.out::println);
    }

    /**
     * 读取少于1000行的excle，可以指定sheet和从几行读起
     */
    @org.junit.Test
    public void readLessThan1000RowBySheet(){
        String filePath = "/Users/jerry/Downloads/测试.xlsx";
        Sheet sheet = new Sheet(1, 1);
        List<Object> objects = ExcelUtil.readLessThan1000RowBySheet(filePath,sheet);
        objects.forEach(System.out::println);
    }

    /**
     * 读取大于1000行的excle
     * 带sheet参数的方法可参照测试方法readLessThan1000RowBySheet()
     */
    @org.junit.Test
    public void readMoreThan1000Row(){
        String filePath = "/Users/jerry/Downloads/测试.xlsx";
        List<Object> objects = ExcelUtil.readMoreThan1000Row(filePath);
        objects.forEach(System.out::println);
    }


    /**
     * 生成excle
     * 带sheet参数的方法可参照测试方法readLessThan1000RowBySheet()
     */
    @org.junit.Test
    public void writeBySimple(){
        String filePath = "/Users/jerry/Downloads/测试.xlsx";
        List<List<Object>> data = new ArrayList<>();
        data.add(Arrays.asList("111","222","333"));
        data.add(Arrays.asList("111","222","333"));
        data.add(Arrays.asList("111","222","333"));
        List<String> head = Arrays.asList("表头1", "表头2", "表头3");
        ExcelUtil.writeBySimple(filePath,data,head);
    }


    /**
     * 生成excle, 带用模型
     * 带sheet参数的方法可参照测试方法readLessThan1000RowBySheet()
     */
    @org.junit.Test
    public void writeWithTemplate(){
        String filePath = "/Users/jerry/Downloads/测试.xlsx";
        ArrayList<TableHeaderExcelProperty> data = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            TableHeaderExcelProperty tableHeaderExcelProperty = new TableHeaderExcelProperty();
            tableHeaderExcelProperty.setName("cmj" + i);
            tableHeaderExcelProperty.setAge(22 + i);
            tableHeaderExcelProperty.setSchool("清华大学" + i);
            data.add(tableHeaderExcelProperty);
        }
        ExcelUtil.writeWithTemplate(filePath,data);
    }


    /**
     * 生成excle, 带用模型,带多个sheet
     */
    @org.junit.Test
    public void writeWithMultipleSheel(){
        ArrayList<ExcelUtil.MultipleSheelPropety> list1 = new ArrayList<>();
        for(int j = 1; j < 4; j++){
            ArrayList<TableHeaderExcelProperty> list = new ArrayList<>();
            for(int i = 0; i < 4; i++){
                TableHeaderExcelProperty tableHeaderExcelProperty = new TableHeaderExcelProperty();
                tableHeaderExcelProperty.setName("cmj" + i);
                tableHeaderExcelProperty.setAge(22 + i);
                tableHeaderExcelProperty.setSchool("清华大学" + i);
                list.add(tableHeaderExcelProperty);
            }

            Sheet sheet = new Sheet(j, 0);
            sheet.setSheetName("sheet" + j);

            ExcelUtil.MultipleSheelPropety multipleSheelPropety = new ExcelUtil.MultipleSheelPropety();
            multipleSheelPropety.setData(list);
            multipleSheelPropety.setSheet(sheet);

            list1.add(multipleSheelPropety);

        }

        ExcelUtil.writeWithMultipleSheel("/home/chenmingjian/Downloads/aaa.xlsx",list1);

    }

}


