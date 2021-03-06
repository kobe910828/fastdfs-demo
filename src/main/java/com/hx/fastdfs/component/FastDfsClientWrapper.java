package com.hx.fastdfs.component;

import com.hx.fastdfs.config.AppConfig;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2018/11/4 10:22
 */
@Component
public class FastDfsClientWrapper {
    private final Logger logger = LoggerFactory.getLogger(FastDfsClientWrapper.class);

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private AppConfig appConfig;

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public StorePath uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return storePath;
    }

    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件内容
     * @param fileExtension
     * @return
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream,buff.length, fileExtension,null);
        return getResAccessUrl(storePath);
    }

    /**
     * 封装图片完整URL地址
     * @param storePath
     * @return
     */
    public String getResAccessUrl(StorePath storePath) {
        String fileUrl = AppConfig.HTTP_PRODOCOL + appConfig.getResHost()
                + ":" + appConfig.getFdfsStoragePort() + "/" + storePath.getFullPath();
        return fileUrl;
    }

    /**
     * 封装图片相对URI地址
     * @param storePath
     * @return
     */
    public String getResUri(StorePath storePath) {
        String uri = "/" + storePath.getFullPath();
        return uri;
    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }
}
