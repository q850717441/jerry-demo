package com.jerry.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: Jerry
 * @create: 2020-08-06 00:38
 * @description: 图片上传-方案1
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ImgVO {
    private String url;        //图片存储地址
    private Integer width;        //图片宽度
    private Integer height;        //图片高度
}