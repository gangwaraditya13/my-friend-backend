package com.adish.myfriend.controller;

import com.adish.myfriend.entities.User;
import com.adish.myfriend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController{

    @Autowired
    private AdminService adminService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUser(){
        List<User> allUser = adminService.getAllUser();
        if(!allUser.isEmpty() && allUser==null){
            return new ResponseEntity<>(allUser, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/m-admin/{userName}")//make admin
    public ResponseEntity<User> updateUser(@RequestBody String userName) {
        boolean check = adminService.updateUserRoll(userName);
        if (check) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
