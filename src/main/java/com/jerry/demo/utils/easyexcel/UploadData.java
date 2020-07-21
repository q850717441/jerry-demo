package com.jerry.demo.utils.easyexcel;

import java.util.Date;

import lombok.Data;

/**
 * 基础数据类
 *
 * @author Jiaju Zhuang
 **/
@Data
public class UploadData {
    private String string;
    private Date date;
    private Double doubleData;
}