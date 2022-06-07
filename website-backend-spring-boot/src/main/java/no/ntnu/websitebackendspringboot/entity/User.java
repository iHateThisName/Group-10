package no.ntnu.websitebackendspringboot.entity;

import static javax.persistence.GenerationType.IDENTITY;


import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "Id")
  private Long id;
  @Column(name = "Name")
  private String name;
  @Column(name = "Username")
  private String username;
  @Column(length = 60, name = "Password")
  private String password;

  //Eager makes it load all the roles whenever it loads the user
  //so when it loads the user it will also load all the roles in the db
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role",
      joinColumns = @JoinColumn(name="user_Id"),
      inverseJoinColumns = @JoinColumn(name="role_Id")
  )
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

  /**
   * Add a role to the user
   *
   * @param role Role to add
   */
  public void addRole(Role role) {
    roles.add(role);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", roles=" + roles +
        '}';
  }
}

