package com.jerry.demo.common.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

/**
 * @author: Jerry
 * @create: 2020-10-28 13:14
 * @description:
 */
public interface IBaseEnum<T extends Serializable>  extends IEnum<T> {
    String getDescription();
}
