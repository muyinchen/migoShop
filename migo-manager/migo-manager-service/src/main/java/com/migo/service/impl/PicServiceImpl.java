package com.migo.service.impl;

import com.migo.pojo.PictureResult;
import com.migo.service.PicService;
import com.migo.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author  知秋
 * Created by kauw on 2016/10/4.
 */
@Service
public class PicServiceImpl implements PicService{
    @Value("${IMAGE_SERVER_BASE_URL}")
    private String IMAGE_SERVER_BASE_URL;
    @Override
    public PictureResult uploadPic(MultipartFile picFile) {
        PictureResult result=new PictureResult();
        //判断图片是否为空
        if (picFile.isEmpty()) {
            result.setError(1);
            result.setMessage("图片为空");
            return result;
        }
        try {
            //上传图片到服务器
            String originalFilename=picFile.getOriginalFilename();
            //取扩展名
            String extendName=originalFilename.substring(originalFilename.indexOf(".")+1);
            FastDFSClient client=new FastDFSClient("classpath:resource/client.conf");
            String url=client.uploadFile(picFile.getBytes(),extendName);
            //拼接URL
            url=IMAGE_SERVER_BASE_URL+url;
            //把 URL响应给客户端
            result.setError(0);
            result.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(1);
            result.setMessage("图片上传失败");
        }
        return result;
    }
}
