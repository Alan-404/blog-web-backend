package com.blogalanai01.server.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.blogalanai01.server.enums.BlogState;

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
    private String categoryId;
    private String userId;
    private String title;
    private String intro;
    private String content;
    private BlogState state;
    

    public void setBlogState(BlogState state){
        this.state = state;
    }

    public BlogState getBlogState(){
        return this.state;
    }
}
