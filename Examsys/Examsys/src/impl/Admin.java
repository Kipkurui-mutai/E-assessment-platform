package impl;

//Implementation for Admin 
public class Admin {
//    Variables

    private int id;
    private String username;
    private String email;
    private String password;

//    Constructors
    public Admin() {
    }

    public Admin(int id,String username, String email, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

//    Getters and Setters for variables
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
