package com.fastdfs.demo.controller;

import com.fastdfs.demo.component.FastDfsClientWrapper;
import com.fastdfs.demo.vo.FileUploadResultVo;
import com.fastdfs.demo.vo.ObjectRestResponse;
import com.github.tobato.fastdfs.domain.StorePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2018/11/4 10:37
 */
@RestController
@RequestMapping("/file")
public class FastDfsController {
    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    /**
     * 上传文件：图片
     * @param file
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/onefile/upload")
    @SuppressWarnings("unchecked")
    public Object uploadFile(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StorePath storePath = fastDfsClientWrapper.uploadFile(file);
        FileUploadResultVo resultVo=new FileUploadResultVo();
        resultVo.setHttpUrl(fastDfsClientWrapper.getResAccessUrl(storePath));
        resultVo.setUri(fastDfsClientWrapper.getResUri(storePath));
        return new ObjectRestResponse<FileUploadResultVo>().rel(true).data(resultVo);
    }

    /**
     * 删除文件
     * @param fileUri
     * @return
     */
    @PostMapping(value = "/onefile/delete")
    public Object uploadFile(String fileUri){
        fastDfsClientWrapper.deleteFile(fileUri);
        return new ObjectRestResponse<FileUploadResultVo>().rel(true);
    }
}
