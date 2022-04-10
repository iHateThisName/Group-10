package no.ntnu.websitebackendspringboot.models;

import static javax.persistence.GenerationType.AUTO;


import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
// @Entity is to tell spring boot that this class is entity in the db.
@Entity(name = "users")
public class User {

  //@Id tells that the attribute below is id in the db table
  //@GeneratedValue tells the db how to generate this id
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;
  private String name;
  private String username;
  private String password;
//  private boolean active = true;

  //Eager makes it load all the roles whenever it loads the user
  //so when it loads the user it will also load all the roles in the db
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Role> roles = new LinkedHashSet<>();

  /**
   * The JPA need an empty constructor
   */
  public User() {
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public User(String username, String password, String name) {
    this.username = username;
    this.name = name;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

//  public boolean isActive() {
//    return active;
//  }
//
//  public void setActive(boolean active) {
//    this.active = active;
//  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}

