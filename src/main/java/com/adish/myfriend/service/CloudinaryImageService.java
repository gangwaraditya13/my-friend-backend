package com.adish.myfriend.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CloudinaryImageService {
    public Map uplaod(MultipartFile multipartFile);

    public Map deleteImage(String publicId);

    public Map deleteAllImages(List<String> publicIds);

    public  Map updateImage(String publicId);
}
