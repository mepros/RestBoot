package com.example.boot.dao;

import com.example.boot.model.Role;
import com.example.boot.model.User;
import java.util.List;

public interface UserDao {
   void addUser(User user);

   void updateUser(User user);

   void removeUser(long id);

   User getUserById(long id);

   List<User> listUser();

   List<Role> listRoles();

   User getUserByName(String userName);

   Role getRoleByName(String name);
}
