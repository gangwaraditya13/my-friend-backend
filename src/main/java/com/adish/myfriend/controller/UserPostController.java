package com.adish.myfriend.controller;

import com.adish.myfriend.entities.UserPost;
import com.adish.myfriend.service.UserPostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@SecurityRequirement(name = "basicAuth")
@Controller
@RequestMapping("/user-post")
public class UserPostController {
    @Autowired
    private UserPostService userPostService;

    @GetMapping("/get-user-posts")
    public ResponseEntity<?> getAllPost(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<UserPost> allUserPost = userPostService.getAllUserPost(userName);
        if(!allUserPost.isEmpty()){
            return new ResponseEntity<>(allUserPost,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-user-post/{myId}")
    public ResponseEntity<?> getPost(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<UserPost> userPost = userPostService.getUserPost(userName, myId);
        if(userPost.isPresent()){
            return new ResponseEntity<>(userPost,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<?> newPost(@RequestBody UserPost userPost){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean check = userPostService.saveNewPost(userPost, userName);
        if(check) {
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{myId}")
    public ResponseEntity<?> updatePost(@RequestBody UserPost userPost, @PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userPostService.updatePost(userPost,userName,myId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-post/{postId}")
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
