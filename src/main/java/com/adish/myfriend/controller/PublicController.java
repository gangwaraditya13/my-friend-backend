package com.adish.myfriend.controller;

import com.adish.myfriend.entities.User;
import com.adish.myfriend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> sighUp(@RequestBody User user){
        boolean check = userService.saveNewUser(user);
        if(check){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}