/**
 * 
 */
package de.tixus.mb.opac.shared.entities;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * @author TSP
 * 
 */
//@Entity
public class Person implements Serializable {

  public TypeOfPerson getType() {
    return type;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Gender getGender() {
    return gender;
  }

  public String getUserName() {
    return userName;
  }

  public String getEmail() {
    return email;
  }

  private static final long serialVersionUID = 1L;

  @Id
  private String id;
  private TypeOfPerson type;
  private String firstName;
  private String lastName;
  private Gender gender;

  private String userName;
  private String password;
  private String email;

  public Person() {
  }

  public Person(final String id, final TypeOfPerson type, final String firstName, final String lastName, final Gender gender,
                final String userName, final String password, final String email) {
    this.id = id;
    this.type = type;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.userName = userName;
    this.password = password;
    this.email = email;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("Person [id=");
    builder.append(id);
    builder.append(", type=");
    builder.append(type);
    builder.append(", firstName=");
    builder.append(firstName);
    builder.append(", lastName=");
    builder.append(lastName);
    builder.append(", gender=");
    builder.append(gender);
    builder.append(", userName=");
    builder.append(userName);
    builder.append(", password=");
    builder.append(password);
    builder.append(", email=");
    builder.append(email);
    builder.append("]");
    return builder.toString();
  }

  public String getId() {
    return id;
  }

}
