package com.adish.myfriend.service.impl;

import com.adish.myfriend.service.CloudinaryImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryImageServiceImpl implements CloudinaryImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map uplaod(MultipartFile multipartFile) {

        try {
            if(!multipartFile.isEmpty()) {
                Map data = cloudinary.uploader().upload(multipartFile.getBytes(), Map.of());
                return data;
            }else{
                return null;
            }
        } catch (IOException e) {
            log.error("In CloudinaryImageServiceImp class" + e);
            throw new RuntimeException("Image uploading fail ..."+e);
        }
    }


    //have to build

    @Override
    public Map deleteImage(String publicIds){
        try {
            Map deleteData = cloudinary.api().deleteResources(Arrays.asList(publicIds), ObjectUtils.emptyMap());
            return deleteData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map deleteAllImages(List<String> publicIds){
        try {
            Map deleteAllData = cloudinary.api().deleteResources(publicIds,ObjectUtils.emptyMap());
            return deleteAllData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  Map updateImage(String publicId){
        try {
            Map updateImage = cloudinary.api().update(publicId,ObjectUtils.emptyMap());
            return updateImage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
