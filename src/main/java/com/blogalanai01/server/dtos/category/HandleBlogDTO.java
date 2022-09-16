package com.blogalanai01.server.dtos.category;

import com.blogalanai01.server.enums.BlogState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HandleBlogDTO {
    private String id;
    private BlogState state;

    public void setBlogState(BlogState state){
        this.state = state;
    }

    public BlogState getBlogState(){
        return this.state;
    }
}


