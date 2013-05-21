package model;

import org.hibernate.validator.constraints.*;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection="users")
public class User {

    @Id
    private String id;

    @NotEmpty(message="Not null!")
    private String name;

    @NotEmpty(message="Not null!")
    private String surname;

    @NotEmpty(message="Not null!")
    private String login;

    @NotEmpty(message="Not null!")
    @Email(message = "Not valid email")
    private String email;

    @NotEmpty(message="Not null!")
    private String password;

    private Date updatedAt;

    private String sessionCode;

    private Date codeUpdatedAt;

    private Role role;

    private boolean active;

    public User() {
        super();
        active=true;
        role=new Role();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public Date getCodeUpdatedAt() {
        return codeUpdatedAt;
    }

    public void setCodeUpdatedAt(Date codeUpdatedAt) {
        this.codeUpdatedAt = codeUpdatedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", active=" + active +
                '}';
    }
}
