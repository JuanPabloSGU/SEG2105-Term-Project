package com.example.seg2105;

public class UserView {
    public String name;
    public String role;
    public String id;

    public UserView(String name, String role, String id){
        this.name = name;
        this.role = role;
        this.id = id;
    }

    public String getUsername(){
        return this.name;
    }
}
