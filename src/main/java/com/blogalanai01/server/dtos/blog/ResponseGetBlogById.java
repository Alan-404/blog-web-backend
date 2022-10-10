package com.blogalanai01.server.dtos.blog;

import com.blogalanai01.server.models.Blog;
import com.blogalanai01.server.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGetBlogById {
    private Blog blog;
    private User author;
}
