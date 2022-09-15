package com.blogalanai01.server.dtos.user;

import com.blogalanai01.server.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAddUserDTO {
    private boolean success;
    private User user;
    private String message;

    public void setAllAttrs(boolean success, User user, String message){
        this.success = success;
        this.user = user;
        this.message = message;
    }
}
