package com.jerry.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JdGoods {
    // 商品名称
    private String title;
    // 商品价格
    private String price;
    // 商品封面
    private String img;
}