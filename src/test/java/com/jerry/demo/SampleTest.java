package com.jerry.demo;

import com.jerry.demo.common.enums.UserState;
import com.jerry.demo.domain.User;
import com.jerry.demo.mapper.UserMapper;
import com.jerry.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Jerry
 * @create: 2020-05-26 00:40
 * @description:
 */

@SpringBootTest
public class SampleTest {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
//        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);



    }

    @Test
    public void testAdd(){
        User user = new User().setName("Jerry4").setAge(10).setEmail("850717441@qq.com").setUserState(UserState.ACTIVE);
        userService.saveOrUpdate(user);
    }


}