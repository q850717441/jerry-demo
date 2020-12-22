package com.jerry.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.jerry.demo.common.enums.UserState;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: Jerry
 * @create: 2020-05-26 00:38
 * @description:
 */
@Data
@TableName(value = "user")
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    @TableField(value = "age")
    private Integer age;
    private String email;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    private UserState userState;
}
