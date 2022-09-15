package com.blogalanai01.server.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "blog")
public class Blog {
    @Id
    private String id;
    private String title;
    private String intro;
    private String content;
    private String createdAt;
    private String modifiedAt;
}
