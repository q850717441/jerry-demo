package com.jerry.demo.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jerry.demo.domain.User;
import com.jerry.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findAll() {
        List<User> users = userMapper.selectList(null);
        users.forEach(e -> System.out.println(e.toString()));
    }

    @Test
    public void insert() {
        User user = new User();
        user.setAge(10).setEmail("850717441@qq.com").setName("姜涛");
        userMapper.insert(user);
        System.out.println("user = " + user.getId());
    }


    @Test
    public void pageSelect() {
        Page<User> page = new Page<>();

        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
//         page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        userMapper.selectPageVo(page);
    }
}