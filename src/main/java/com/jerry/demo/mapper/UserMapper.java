package com.jerry.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jerry.demo.domain.User;
import org.apache.ibatis.annotations.Select;

/**
 * @author: Jerry
 * @create: 2020-05-26 00:39
 * @description:
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where id = #{id}")
    User findUserById(int id);

    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @return 分页对象
     */
    IPage<User> selectPageVo(Page<?> page);

}
