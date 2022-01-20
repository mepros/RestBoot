package com.example.boot.controller;

import com.example.boot.model.User;
import com.example.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class UsersRestController {

    private final UserService userService;

    @Autowired
    UsersRestController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/restUsers")
    public ResponseEntity<List<User>> restListUsers(){
        List<User> users = userService.listUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/restUsers/{id}")
    public ResponseEntity<User> restOneUser(@PathVariable long id){
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/restUsers")
    public ResponseEntity<User> newUser(@RequestBody User user, String[] roleNames) {
        userService.addUser(user, roleNames);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/restUsers")
    public ResponseEntity<User>  updateUser(@RequestBody User user, String[] roleNames) {
        userService.updateUser(user, roleNames);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/restUsers/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
