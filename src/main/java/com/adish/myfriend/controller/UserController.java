package com.adish.myfriend.controller;

import com.adish.myfriend.Component.*;
import com.adish.myfriend.service.CloudinaryImageService;
import com.adish.myfriend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@SecurityRequirement(name = "basicAuth")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    @Operation(
            summary = "Upload profile image",
            description = "Uploads user profile image to Cloudinary"
    )
    @PostMapping("/image-upload")
    public ResponseEntity<Map> uploadImage(@RequestParam("image") MultipartFile file){
        Map data = cloudinaryImageService.uplaod(file);
        if(!data.isEmpty()) {
            return new ResponseEntity<>(data, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        responceUser info = userService.getInfo(userName);
        if(info == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(info,HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updateUserPassword(@RequestBody PasswordReset passwordReset){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean check = userService.updatePassword(passwordReset, userName);
        if(check){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-photo")
    public ResponseEntity<?> updateUserPhotoURL(@RequestBody NewPhotoURL newPhotoURL){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean check = userService.updatePhotoURL(newPhotoURL, userName);
        if(check){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UpdateGmailOrUserName user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean check = userService.updateUser(user, userName);
        if(check){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestBody PasswordRequest passwordRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean check = userService.deleteUser(passwordRequest.getPassword(), userName);
        if(check){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
