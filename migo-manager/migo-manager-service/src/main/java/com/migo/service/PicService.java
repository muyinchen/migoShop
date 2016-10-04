package com.migo.service;

import com.migo.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author  知秋
 * Created by kauw on 2016/10/4.
 */
public interface PicService {
    PictureResult uploadPic(MultipartFile picFile);
}
