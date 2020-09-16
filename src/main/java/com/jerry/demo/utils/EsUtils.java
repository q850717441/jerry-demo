package com.jerry.demo.utils;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: Jerry
 * @create: 2020-09-16 10:42
 * @update: 2020-09-16 10:42
 * @description: Es常用方法工具类
 */
@Component
public class EsUtils<T> {
    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 判断索引是否存在
     */
    public boolean existsIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        return exists;
    }

    /**
     * 创建索引
     */
    public boolean createIndex(String index) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(index);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged();
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(String index) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        AcknowledgedResponse response = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 判断某索引下文档id是否存在 * @param index
     */
    public boolean docExists(String index, String id) throws IOException {
        //只判断索引是否存在不需要获取_source getRequest.fetchSourceContext(new FetchSourceContext(false)); getRequest.storedFields("_none_");
        GetRequest getRequest = new GetRequest(index, id);
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        return exists;
    }

    /**
     * 添加文档记录
     * @param index
     * @param id
     * @param t 要添加的数据实体类 * @return
     * @throws IOException
     */
    public boolean addDoc(String index, String id, T t) throws IOException {
        // 创建请求
        IndexRequest request = new IndexRequest(index);
        // 规则
        request.id(id);
        //timeout
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        request.source(JSON.toJSONString(t), XContentType.JSON);
        // 发送请求
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        RestStatus status = indexResponse.status();
        return status == RestStatus.OK || status == RestStatus.CREATED;
    }

    /**
     * 根据id来获取记录
     * @param index
     * @param id
     * @return
     * @throws IOException */
    public GetResponse getDoc(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        return getResponse;
    }

    /**
     * 批量添加文档记录
     * 没有设置id ES会自动生成一个，如果要设置 IndexRequest的对象.id()即可 * @param index
     * @param list
     * @return
     * @throws IOException
     */
    public boolean bulkAdd(String index, List<T> list) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        //timeout
        bulkRequest.timeout(TimeValue.timeValueMinutes(2));
        bulkRequest.timeout("2m");
        for (int i = 0; i < list.size(); i++) {
            bulkRequest.add(new IndexRequest(index).source(JSON.toJSONString(list.get(i)), XContentType.JSON));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulkResponse.hasFailures();
    }

    /**
     * 更新文档记录
     */
    public boolean updateDoc(String index, String id, T t) throws IOException {
        UpdateRequest request = new UpdateRequest(index, id);
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        request.doc(JSON.toJSONString(t), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        return updateResponse.status() == RestStatus.OK;
    }


    /**
     * 删除文档记录
     */
    public boolean deleteDoc(String index, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, id);
        //timeout
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        return deleteResponse.status() == RestStatus.OK;
    }

    /**
     * 查询搜索
     * @param index 索引
     * @param field 字段
     * @param keyword 关键词
     * @param pageNo 起始位置
     * @param size 查询数量
     */
    public SearchResponse search(String index, String field, String keyword, Integer pageNo, Integer size) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        /**
         * 使用QueryBuilder
         * termQuery("keyword", obj) 完全匹配,根据某字段来搜索
         * termsQuery("keyword", obj1, obj2..) 一次匹配多个值
         * matchQuery("keyword", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
         * multiMatchQuery("text", "field1", "field2"..); 匹配多个字段, field有通配符也行
         * matchAllQuery(); 匹配所有文件
         */
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(field, keyword);
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(field, keyword);
        sourceBuilder.query(termQueryBuilder);
        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(size);
        //最大搜索时间。
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse;
    }

    /**
     * 实现搜索功能，带高亮
     */
    public List<Map<String, Object>> searchContentHighlighter(String index, String field, String keyword, int pageNo, int pageSize) throws IOException {
        // 基本的参数判断!
        if (pageNo <= 0) {
            pageNo = 0;
        }
        // 基本的条件搜索
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 精准匹配 QueryBuilders 根据自己要求配置查询条件即可!
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(field, keyword);
        sourceBuilder.query(termQueryBuilder);
        // 高亮构建!
        HighlightBuilder highlightBuilder = new HighlightBuilder(); //生成高亮查询器
        //高亮查询字段
        highlightBuilder.field(field);
        //如果要多个字段高亮,这项要为false
        highlightBuilder.requireFieldMatch(false);
        //高亮设置
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        // 分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);
        //最大搜索时间。
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 搜索
        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 解析结果!
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            //获取高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField titleField = highlightFields.get(field);
            Map<String, Object> source = hit.getSourceAsMap();
            //千万记得要记得判断是不是为空,不然你匹配的第一个结果没有高亮内容,那么就会报空指 针异常
            if (titleField != null) {
                Text[] fragments = titleField.fragments();
                StringBuilder name = new StringBuilder();
                for (Text text : fragments) {
                    name.append(text);
                }
                source.put(field, name.toString());//高亮字段替换掉原本的内容
            }
            list.add(source);
        }
        return list;
    }
}
