package com.adish.myfriend.controller;

import com.adish.myfriend.entities.UserPost;
import com.adish.myfriend.service.UserPostService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user-post")
public class UserPostController {
    @Autowired
    private UserPostService userPostService;

    @GetMapping
    public ResponseEntity<?> newPost(@RequestBody UserPost userPost){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userPostService.saveNewPost(userPost,userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{myId}")
    public ResponseEntity<?> updatePost(@RequestBody UserPost userPost, @PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userPostService.updatePost(userPost,userName,myId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/Delete-post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable ObjectId postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean check = userPostService.deletePostById(postId, userName);
        if(check){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
