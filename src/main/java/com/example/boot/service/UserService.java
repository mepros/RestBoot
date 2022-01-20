package com.example.boot.service;

import com.example.boot.model.Role;
import com.example.boot.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user, String[] roleNames);

    User updateUser(User user, String[] roleNames);

    void removeUser(long id);

    User getUserById(long id);

    List<User> listUser();

    List<Role> listRoles();

    User getUserByName(String userName);

    Role getRoleByName(String name);

}
