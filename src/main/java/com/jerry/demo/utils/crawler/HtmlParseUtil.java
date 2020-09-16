package com.jerry.demo.utils.crawler;

import com.jerry.demo.domain.JdGoods;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jerry
 * @create: 2020-09-16 15:05
 * @update: 2020-09-16 15:05
 * @description: 抓取京东搜索的商品
 */
public class HtmlParseUtil {

    public static void main(String[] args) throws Exception {
        parseJD("vue").forEach(System.out::println);
    }

    /**
     * 抓取京东搜索的商品
     * @param keywords 要搜索的关键字
     * @return 抓取的商品集合
     */
    public static List<JdGoods> parseJD(String keywords) throws Exception {
        String url = "https://search.jd.com/Search?keyword=" + keywords;
        Document document = Jsoup.parse(new URL(url), 30000);
        Element element = document.getElementById("J_goodsList");
        Elements elements = element.getElementsByTag("li");
        ArrayList<JdGoods> goodsList = new ArrayList<>();
        // 获取京东的商品信息
        for (Element el : elements) {
            String img = el.getElementsByTag("img").eq(0).attr("src");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            // 封装获取的数据
            JdGoods content = new JdGoods();
            content.setImg(img);
            content.setPrice(price);
            content.setTitle(title);
            goodsList.add(content);
        }
        return goodsList;
    }
}
