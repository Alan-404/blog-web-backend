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
public class ResponseGetUserDTO {
    private boolean success;
    private User user;
    private boolean role;
}
