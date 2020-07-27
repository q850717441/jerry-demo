package com.jerry.demo.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Jerry
 * @create: 2020-07-25 13:57
 * @description:
 */
@Data
@Accessors(chain = true)
public class VideoVO {
    private Integer projectId;
    private String name;//视频名称
    private String file;//文件路径
    private String type;//固定

    public VideoVO() {
        this.type = "video";
    }
}
