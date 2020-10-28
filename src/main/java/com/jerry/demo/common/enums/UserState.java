package com.jerry.demo.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author: Jerry
 * @create: 2020-10-28 13:15
 * @description:
 */
@Getter
public enum UserState  {
    /** 启用 */
    ACTIVE(1,"A"),
    /** 停用 */
    INACTIVE(0,"I")
    ;

    @EnumValue
    private final int state;

    private final String descp;

    UserState(int state, String descp) {
        this.state = state;
        this.descp = descp;
    }
}
