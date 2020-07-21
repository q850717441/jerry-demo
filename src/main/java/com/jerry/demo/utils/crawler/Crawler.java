package com.jerry.demo.utils.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author: Jerry
 * @create: 2020-07-01 10:21
 * @description: 爬虫-知乎
 */
public class Crawler {
    public static void main(String[] args) throws IOException {
        //下载图片网址  自己更换链接
        String url1 = "https://www.zhihu.com/question/50734809";
        Connection connection = Jsoup.connect(url1);
        //下载到本地地址
        String filePath = "/Users/jerry/Files/crawler";
        File file = new File(filePath);
        System.out.println(filePath + "创建：" + file.mkdirs());
        Document document = connection.get();//获取整个页面的对象
        Element questionMain = document.selectFirst("[class=Question-main]");
        Element QuestionAnswersanswer = questionMain.selectFirst("[class=QuestionAnswers-answers]");
        Elements bigDivs = QuestionAnswersanswer.select("[class=List-item]");//找寻所有的class="List-item"

        int count = 0;//总共下载了多少
        for (Element e : bigDivs) {
            Element answer = e.selectFirst("[class=ContentItem AnswerItem]");
            String names = answer.attr("data-zop");
            int left = names.indexOf(":");
            int right = names.indexOf(",");
            //获取作者
            String name = names.substring((left + 2), (right - 1));
            Element e1 = e.selectFirst("[class=RichContent-inner]");
            //获取figure标题群
            Elements figures = e1.select("figure");
            int a = 0;//每一个用户一共多少张
            for (Element ele : figures) {
                a++;
                Element elementImg = ele.selectFirst("img");
                String src = elementImg.attr("src");

                URL url = new URL(src);
                InputStream ips = url.openStream();

                FileOutputStream fos = new FileOutputStream(filePath + "//" + name + a + ".jpg");
                System.out.println(name + a);
                byte[] b = new byte[1024];
                int i = ips.read(b);
                while (i != -1) {
                    fos.write(b, 0, i);
                    fos.flush();
                    i = ips.read(b);
                }
                fos.close();
                ips.close();
                count++;
                System.out.println("下载" + count + "个了");
            }
        }
    }
}

