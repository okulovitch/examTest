package com.exam.com.activities;

/**
 * Created by Ge62 on 08.01.2018.
 */

public class UserInformation {


    private String Name;
    private String Email;
    private String Password;

    public UserInformation(){}
    public UserInformation(String email){
        this.Email = email;
    }


    public UserInformation(String Name,String Email,String Password){

        this.Name=Name;
        this.Email=Email;
        this.Password=Password;

    }

    public String getName(){
        return Name;
    }
    public String getEmail(){
        return Email;
    }
    public String getPassword(){
        return Password;
    }
}
