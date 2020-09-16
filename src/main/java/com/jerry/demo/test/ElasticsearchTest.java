package com.jerry.demo.test;

import com.alibaba.fastjson.JSON;
import com.jerry.demo.domain.User;
import com.jerry.demo.mapper.UserMapper;
import com.jerry.demo.utils.EsUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: Jerry
 * @create: 2020-09-16 11:14
 * @update: 2020-09-16 11:14
 * @description: Es APIs 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTest {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private EsUtils<User> esUtils;

    /**
     * 测试创建索引
     */
    @Test
    public void testCreateIndex() throws IOException {
        boolean result = esUtils.createIndex("jerry_index");
        System.out.println(result);
    }

    /**
     * 测试获取索引
     */
    @Test
    public void testExistsIndex() throws IOException {
        boolean exists = esUtils.existsIndex("jerry_index");
        System.out.println(exists);
    }

    /**
     * 测试删除索引
     */
    @Test
    public void testDeleteIndexRequest() throws IOException {
        boolean result = esUtils.deleteIndex("jerry_index");
        System.out.println(result);
    }

    /**
     * 测试添加文档记录
     */
    @Test
    public void testAddDocument() throws IOException {
        // 创建对象
        User user = new User(null, "姜涛", 23, "850717441@qq.com");
        boolean result = esUtils.addDoc("jerry_index", null, user);
        System.out.println(result);
    }

    /**
     * 判断此id是否存在这个索引库中
     */
    @Test
    public void testIsExists() throws IOException {
        boolean exists = esUtils.docExists("jerry_index", "W88BlXQBwaEuUQJA921X");
        System.out.println(exists);
    }

    /**
     * 根据id获取记录
     */
    @Test
    public void testGetDocument() throws IOException {
        GetResponse getResponse = esUtils.getDoc("jerry_index", "1");
        System.out.println(getResponse.getSourceAsString()); // 打印文档内容
        System.out.println(getResponse);
    }

    /**
     * 更新文档记录
     */
    @Test
    public void testUpdateDocument() throws IOException {
        User user = new User(null, "姜涛", null, "850717441@qq.com");
        boolean result = esUtils.updateDoc("jerry_index", "1", user);
        System.out.println(result);
    }

    /**
     * 删除文档记录
     */
    @Test
    public void testDelete() throws IOException {
        boolean result = esUtils.deleteDoc("jerry_index", "1");
        System.out.println(result);
    }

    /**
     * 批量添加文档
     */
    @Test
    public void testBulkRequest() throws IOException {
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User(null, "姜涛1", 1, "850717441@qq.com"));
        userList.add(new User(null, "你好2", 2, "850717441@qq.com"));
        userList.add(new User(null, "张三", 3, "850717441@qq.com"));
        userList.add(new User(null, "李四", 4, "850717441@qq.com"));
        userList.add(new User(null, "王鹏", 5, "850717441@qq.com"));
        userList.add(new User(null, "张家辉", 6, "850717441@qq.com"));
        userList.add(new User(null, "彭丽媛", 7, "850717441@qq.com"));
        boolean result = esUtils.bulkAdd("jerry_index", userList);
        System.out.println(result);
    }

    /**
     * 查询测试
     */
    @Test
    public void testSearch() throws IOException {
        SearchResponse response = esUtils.search("jerry_index", "email", "", 0, 10);
        System.out.println(JSON.toJSONString(response.getHits()));
        System.out.println("================SearchHit==================");
        for (SearchHit documentFields : response.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }
    }


    /**
     * 搜索高亮测试
     */
    @Test
    public void testSearchContentHighlighter() throws IOException {
        List<Map<String, Object>> list = esUtils.searchContentHighlighter("jerry_index", "name","张", 0, 10);
        list.forEach(System.out::println);
    }
}

