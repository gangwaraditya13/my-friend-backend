package com.adish.myfriend.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProjectConfig {

    @Bean
    public Cloudinary getCloudinary(){

        Map conifg = new HashMap();

        conifg.put("cloud_name","YOUR_CLOUD_NAME");
        conifg.put("api_key","YOUR_API_KEY");
        conifg.put("api_secret","YOUR_API_SECRET");
        conifg.put("secure",true);

        return new Cloudinary(conifg);
    }

}
