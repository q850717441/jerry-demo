package com.jerry.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Jerry
 * @create: 2020-05-26 00:38
 * @description:
 */
@Data
@TableName(value = "user")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "age")
    private Integer age;
    @TableField(value = "email")
    private String email;

    private UserState userState;
}
