package com.jerry.demo;

import com.jerry.demo.domain.User;
import com.jerry.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);

//        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);

    }

}