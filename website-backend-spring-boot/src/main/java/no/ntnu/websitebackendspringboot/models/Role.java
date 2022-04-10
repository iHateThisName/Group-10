package no.ntnu.websitebackendspringboot.models;

import static javax.persistence.GenerationType.AUTO;

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
  private Long id;
  private String name;

  /**
   * We only need the name when crating a new role,
   * because we have told the database to generate this id.
   *
   * @param name the name of the role.
   */
  public Role(String name) {
    this.name = name;
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
}
