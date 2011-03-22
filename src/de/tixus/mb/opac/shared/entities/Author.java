/**
 * 
 */
package de.tixus.mb.opac.shared.entities;

import java.io.Serializable;

/**
 * @author TSP
 * 
 */
public class Author implements Serializable {

  private static final long serialVersionUID = 1L;

  private String firstName;
  private String lastName;

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Author() {
  }

  public Author(final String firstName, final String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return firstName + " " + lastName;
  }
}
