package com.example.boot.dao;

import com.example.boot.model.Role;
import com.example.boot.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @PersistenceContext
   private EntityManager em;

   @Override
   public void addUser(User user) {
      em.persist(user);
   }

   @Override
   public void updateUser(User user) {
      em.merge(user);
   }

   @Override
   public void removeUser(long id) {
      User user = (User) em.find(User.class, id);
      em.remove(user);
   }

   @Override
   public User getUserById(long id) {
      return (User) em.find(User.class, id);
   }

   @Override
   public List<User> listUser() {
      return em.createQuery("select u from User u").getResultList();
   }

   @Override
   public List<Role> listRoles() {
      return em.createQuery("FROM Role", Role.class).getResultList();
   }

   @Override
   public User getUserByName(String userName) {
      return em.createQuery("select u from User u where u.userName=:username", User.class).setParameter("username", userName).getSingleResult();
   }

   @Override
   public Role getRoleByName(String name) {
      return em.createQuery("select r from Role r where r.role=:rolename", Role.class).setParameter("rolename", name).getSingleResult();
   }
}
