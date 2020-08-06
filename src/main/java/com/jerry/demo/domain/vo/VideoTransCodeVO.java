package com.jerry.demo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author: Jerry
 * @create: 2020-07-25 16:56
 * @description: 接收视频转码对象
 */
@Data
public class VideoTransCodeVO {
    private String status;
    private SourceMetaBean sourceMeta;
    private String title;
    private String key;
    private String type;
    private String id;
    private List<FilesBean> files;

    @Data
    @AllArgsConstructor
    public static class SourceMetaBean {
        /**
         * AudioBitRate : 192k
         * C_FPS : 25
         * DAR : 4:3
         * Start : 0.22
         * VideoBitRate : 461k
         * AudioSampleRate : 48000
         * Duration : 30.98
         * Height : 576
         * Width : 720
         * PixelFormat : yuv420p
         * FPS : 25
         * AudioFormat : mp2
         * SAR : 16:15
         * FileFormat : .mpg
         * VideoFormat : mpeg2video
         */

        private String AudioBitRate;
        private String C_FPS;
        private String DAR;
        private double Start;
        private String VideoBitRate;
        private String AudioSampleRate;
        private double Duration;
        private String Height;
        private String Width;
        private String PixelFormat;
        private String FPS;
        private String AudioFormat;
        private String SAR;
        private String FileFormat;
        private String VideoFormat;
    }

    @Data
    @AllArgsConstructor
    public static class FilesBean {
        private String profileId;
        private String type;
        private String name;
    }


}
