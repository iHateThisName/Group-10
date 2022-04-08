package no.ntnu.websitebackendspringboot.models;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * @author "https://github.com/iHateThisName"
 * @version 1.0
 */
// @Entity is to tell spring boot that this class is entity in the db.
@Entity(name = "users")
public class User {

  //@Id tells that the attribute below is id in the db table
  //@GeneratedValue tells the db how to generate this id
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String username;
  private String password;
  //Eager makes it load all the roles whenever it loads the user
  //so when it loads the user it will also load all the roles in the db
  @ManyToMany(fetch = FetchType.EAGER)
  private Collection<Role> roles = new ArrayList<>();

  /**
   * Need an empty constructor for the JPA
   */
  public User() {
  }

  public User(Long id, String name, String username, String password,
              Collection<Role> roles) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }
}

