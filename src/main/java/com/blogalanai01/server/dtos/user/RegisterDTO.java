package com.blogalanai01.server.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private String userId;
    private MultipartFile avatar;
}
