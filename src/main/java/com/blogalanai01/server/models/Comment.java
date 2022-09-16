package com.blogalanai01.server.models;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    private String id;
    private String userId;
    private String blogId;
    private String content;
    private String reply = "root";
}
