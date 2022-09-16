package com.blogalanai01.server.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "account")
public class Account {
    @Id
    private String id;
    private String password;
    private String userId;
    private boolean role;


    public boolean getRole(){
        return this.role;
    }

    public void setRole(boolean role){
        this.role = role;
    }
}
