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

        conifg.put("cloud_name","dx3wtw7pf");
        conifg.put("api_key","516676559777456");
        conifg.put("api_secret","ghGgw_P7h63mBf7klON14-8hHGo");
        conifg.put("secure",true);

        return new Cloudinary(conifg);
    }

}
