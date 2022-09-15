package com.blogalanai01.server.dtos.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class changePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
