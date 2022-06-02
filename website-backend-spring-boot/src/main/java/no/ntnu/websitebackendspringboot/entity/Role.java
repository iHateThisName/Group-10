package no.ntnu.websitebackendspringboot.entity;

import static javax.persistence.GenerationType.AUTO;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 *
 * This is a model/entity in the database.
 */
// @Entity is to tell spring boot that this class is entity in the db.
@Entity(name = "roles")
public class Role {

  //@Id tells that the attribute below is id in the db table
  //@GeneratedValue tells the db how to generate this id
  @Id
  @GeneratedValue(strategy = AUTO)
  @Column(name = "Id")
  private Long id;
  @Column(name = "Role")
  private String name;

  /**
   * We only need the name when crating a new role,
   * because we have told the database to generate this id.
   *
   * @param roleName the name of the role.
   */
  public Role(String roleName) {
    //making it uppercase
    roleName = roleName.toUpperCase();
    //This string needs to be included
    String stringRole = "ROLE_";

    //Cheeks if the new role name has "ROLE_" in front of it
    if (!roleName.startsWith(stringRole)) {
      //if not that just add it
      roleName = stringRole + roleName;
    }
    this.name = roleName;
  }

  /**
   * The JPA need an empty constructor
   */
  public Role() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Role{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
