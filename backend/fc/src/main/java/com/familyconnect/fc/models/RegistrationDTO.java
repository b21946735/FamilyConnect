package com.familyconnect.fc.models;

public class RegistrationDTO {
    private String email;
    private String password;
    private String name;
    private String role;

    public RegistrationDTO(){
        super();
    }

    public RegistrationDTO(String email,String name, String password , String role){
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getRole(){
        return this.role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String toString(){
        return "Registration info: username: " + this.email + " password: " + this.password + " name: " + this.name + " role: " + this.role;
    }
}
