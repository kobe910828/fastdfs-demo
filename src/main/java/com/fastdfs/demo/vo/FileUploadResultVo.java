package com.fastdfs.demo.vo;

import lombok.Data;
import lombok.ToString;

/**
 * 文件上传Vo
 */
@Data
@ToString
public class FileUploadResultVo {
    /**
     * 访问图片的全路径
     *
     */
    private String httpUrl;
    private String uri;
}

