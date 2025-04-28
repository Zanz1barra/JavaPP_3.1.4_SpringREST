package edu.kata.spring_rest.model;

import java.util.List;

public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private Integer age;
    private List<Role> roles;
    private String username;
    private String password;

    public UserDTO() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }
    public Integer getAge(Integer defaultAge) {
        return age == null ? defaultAge : age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }
    public String getUsername(String defaultUsername) {
        return (username == null || username.isEmpty()) ? defaultUsername : username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public String getPassword(String defaultPassword) {
        return (password == null || password.isEmpty()) ? defaultPassword : password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return this.getClass().getName() +
                " [id = " + id +
                ", username = " + username +
                ", password = " + password +
                ", roles = " + roles +
                ", firstname = " + firstname +
                ", lastname = " + lastname +
                ", age = " + age +
                "]";
    }

    public static UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setAge(user.getAge());
        userDTO.setRoles(user.getRoles());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
}
