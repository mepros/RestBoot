package com.example.boot.controller;

import com.example.boot.model.User;
import com.example.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

	private final UserService userService;

	@Autowired
	UserController(UserService userService){
		this.userService = userService;
	}

	@GetMapping("/admin")
	public String listUsers(Model model){
		model.addAttribute("listUsers", userService.listUser());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userInfo", user);
		model.addAttribute("user", new User());
		return "users";
	}

	@GetMapping("/user")
	public String userInfo(Model model){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("userInfo", user);
		return "user";
	}

}
