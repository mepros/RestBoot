package com.example.boot.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name")
   private String name;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "user_name")
   private String userName;

   @Column(name = "password")
   private String password;

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "user_roles",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "role_id"))
   private Set<Role> roles;

   @Transient
   private String rolesForTable;

   public String getRolesForTable() {
      String rolesForTable = "";
      for(Role role : getRoles()) {
         if (role.getRole().equals("ROLE_ADMIN")){
            rolesForTable = rolesForTable + "\n" + "ADMIN";
         } else if (role.getRole().equals("ROLE_USER")){
            rolesForTable = rolesForTable + "\n" + "USER";
         }
      }
      return rolesForTable;
   }

   public void setRolesForTable(String rolesForTable) {
      this.rolesForTable = rolesForTable;
   }

   public User() {

   }

   public User(String name, String lastName, String userName, String password, Set<Role> roles) {
      this.name = name;
      this.lastName = lastName;
      this.userName = userName;
      this.password = password;
      this.roles = roles;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return roles;
   }

   public String getPassword() {
      return password;
   }

   @Override
   public String getUsername() {
      return userName;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Set<Role> getRoles() {
      return roles;
   }

   public void setRoles(Set<Role> roles) {
      this.roles = roles;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", lastName='" + lastName + '\'' +
              ", userName='" + userName + '\'' +
              '}';
   }
}
