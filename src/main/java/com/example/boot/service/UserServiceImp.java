package com.example.boot.service;


import com.example.boot.dao.UserDao;
import com.example.boot.model.Role;
import com.example.boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImp implements UserService {

   private final UserDao userDao;

   UserServiceImp(UserDao userDao){
      this.userDao = userDao;
   }

   @Transactional
   @Override
   public User addUser(User user, String[] roleNames) {
      Set<Role> rolesSet = new HashSet<>();
      for (String name: roleNames) {
         rolesSet.add(userDao.getRoleByName(name));
      }
      user.setRoles(rolesSet);
      System.out.println(user);
      userDao.addUser(user);
      return user;
   }

   @Transactional
   @Override
   public User updateUser(User user, String[] roleNames) {
      Set<Role> rolesSet = new HashSet<>();
      for (String name: roleNames) {
         rolesSet.add(userDao.getRoleByName(name));
      }
      user.setRoles(rolesSet);
      userDao.updateUser(user);
      return user;
   }

   @Transactional
   @Override
   public void removeUser(long id) {
      userDao.removeUser(id);
   }

   @Transactional
   @Override
   public User getUserById(long id) {
      return userDao.getUserById(id);
   }

   @Transactional
   @Override
   public List<User> listUser() {
      return userDao.listUser();
   }

   @Transactional
   @Override
   public List<Role> listRoles() {
      return userDao.listRoles();
   }

   @Override
   public User getUserByName(String userName) {
      return userDao.getUserByName(userName);
   }

   @Override
   public Role getRoleByName(String name) {
      return userDao.getRoleByName(name);
   }
}
