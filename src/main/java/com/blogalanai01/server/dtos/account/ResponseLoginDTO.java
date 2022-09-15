package com.blogalanai01.server.dtos.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLoginDTO {
    private boolean success;
    private String accessToken;

    public void setAllAttrs(boolean success, String accessToken){
        this.success = success;
        this.accessToken = accessToken;
    }
}
