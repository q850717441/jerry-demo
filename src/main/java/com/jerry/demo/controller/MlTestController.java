package com.jerry.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.jerry.demo.domain.vo.VideoTransCodeVO;
import com.jerry.demo.domain.vo.VideoVO;
import com.jerry.demo.utils.common.DataResult;
import com.jerry.demo.utils.fileutil.FileDownload;
import com.jerry.demo.utils.httputil.HttpUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Jerry
 * @create: 2020-07-25 13:06
 * @description: 测试转码接口
 */
@RestController
@RequestMapping("/ml")
public class MlTestController {
    /** url */
    public static final String App_Url = "http://172.27.243.73/tc3/web";

    @PostMapping("/videoTranscodePost")
    public DataResult videoTranscodePost() {
        String result = init(1);
        return DataResult.success(JSONObject.parseObject(result));
    }

    @PostMapping("/initCallback")
    public DataResult initCallback() {
        //1.将信息存入数据库
        //2.解析请求参数，如果标识成功 修改videoId的pushFlag=1 标识已推送
        //3.如果推送成功，就请求转码任务
        String result = createTask();
        return DataResult.success(JSONObject.parseObject(result));
    }

    @PostMapping("/transcodeCallback")
    public DataResult transcodeCallback(@RequestBody VideoTransCodeVO videoTransCodeVO) throws Exception {
        //todo 1.将信息存入数据库
        //2.根据内容下载视频到服务器
        List<VideoTransCodeVO.FilesBean> files = videoTransCodeVO.getFiles();
        //todo 获取转码视频地址
        String transcodeUrl = files.stream().
                filter(filesBean -> "video".equals(filesBean.getType())).findFirst().get().getName();
        String filePath = FileDownload.downloadFile(transcodeUrl, "/Users/jerry/Downloads/test/");
        //todo 3.修改数据库视频数据。

        String result = createTask();
        return DataResult.success(JSONObject.parseObject(result));
    }


    public static String init(Integer videoId) {
        String url = App_Url + "/api/init.php";
        VideoVO videos = new VideoVO();
        videos.setProjectId(1)
                .setFile("1")
                .setName("1");
        JSONObject data = new JSONObject();
        data.put("key", videoId);//视频ID
        data.put("type", "init");//固定
        data.put("priority", "100");//固定
        data.put("operator", "admin");//固定
        data.put("inputs", videos);//videos
        data.put("signature", "eNmS0Drh45pcXsJWx3dPQG6buMIHnz60");//加密秘钥
        //todo 缺少回调地址
        data.put("callback", "/ml/transcodeCallback");//回调地址
        return HttpUtil.sendPost1(url, data);
    }

    public static String createTask() {
        String url = App_Url + "/api/createtask.php";
        JSONObject inputs = new JSONObject();
        inputs.put("type", "video");
        inputs.put("sourceid", "");//返回的id
        JSONObject outputs = new JSONObject();
        outputs.put("transcodeId", "56");
        outputs.put("watermarkId", "17");//返回的id
        outputs.put("type", "video");//返回的id
        JSONObject data = new JSONObject();
        data.put("key", "");//返回的key
        data.put("type", "transcode");//固定
        data.put("priority", "10");//固定
        data.put("operator", "admin");//固定
        data.put("inputs", inputs);
        data.put("outputs", outputs);
        data.put("callback", "100");//转码回调
        data.put("signature", "eNmS0Drh45pcXsJWx3dPQG6buMIHnz60");//加密秘钥
        return HttpUtil.sendPost1(url, data);
    }


}
