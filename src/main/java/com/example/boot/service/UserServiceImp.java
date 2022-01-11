package com.example.boot.service;


import com.example.boot.dao.UserDao;
import com.example.boot.model.Role;
import com.example.boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public void addUser(User user) {
      userDao.addUser(user);
   }

   @Transactional
   @Override
   public void updateUser(User user) {
      userDao.updateUser(user);
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
