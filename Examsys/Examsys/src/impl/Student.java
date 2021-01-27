package impl;

//Prototype for the student object

import java.sql.Blob;




public class Student {
    
//    variables
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Blob profile;
    
    public Student(){};

    public Student(int id, String firstname, String lastname, Blob profile) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profile = profile;
    }

    public Student(int id, String firstname, String lastname, String email, String password, Blob profile) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

//    Getters and Setters for the variables
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Blob getProfile() {
        return profile;
    }

    public void setProfile(Blob profile) {
        this.profile = profile;
    }
    
    
    
}
