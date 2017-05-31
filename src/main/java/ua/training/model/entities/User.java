package ua.training.model.entities;

public class User {
    private String username;
    private String password;
    private String email;
    private UserGroup group;

    public User(String username, String password, String email, UserGroup group) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.group = group;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }
}
