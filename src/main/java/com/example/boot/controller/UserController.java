package com.example.boot.controller;


import com.example.boot.model.Role;
import com.example.boot.model.User;
import com.example.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;


@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/admin")
	public String listUsers(Model model){
		model.addAttribute("listUsers", userService.listUser());
		return "users";
	}
	@GetMapping("/user")
	public String userInfo(Model model){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("userInfo", user);
		return "user";
	}

	@GetMapping("/add")
	public String create(Model model){
		model.addAttribute("user", new User());
		model.addAttribute("userRoles", userService.listRoles());
		return "add";
	}
	@PostMapping("/save")
	public String createUser(@ModelAttribute("user") User user, String[] roleNames){
		Set<Role> rolesSet = new HashSet<>();
		for (String name: roleNames) {
			rolesSet.add(userService.getRoleByName(name));
		}
		user.setRoles(rolesSet);
		userService.addUser(user);
		return "redirect:/admin";
	}
	@GetMapping("/remove/{id}")
	public String removeUser(@PathVariable("id") long id){
		this.userService.removeUser(id);
		return "redirect:/admin";
	}
	@GetMapping("/edit/{id}")
	public String update(@PathVariable("id") long id, Model model){
		User user =  userService.getUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("selectedRoles", user.getRoles());
		model.addAttribute("existingRoles", userService.listRoles());
		return "edit";
	}
	@PostMapping("/update")
	public String updateUser(@ModelAttribute("user") User user, String[] roleNames){
		Set<Role> rolesSet = new HashSet<>();
		for (String name: roleNames) {
			rolesSet.add(userService.getRoleByName(name));
		}
		user.setRoles(rolesSet);
		userService.updateUser(user);
		return "redirect:/admin";
	}

}
