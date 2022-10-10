package com.blogalanai01.server.dtos.blog;

import java.util.List;

import com.blogalanai01.server.models.Blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseViewBlogs {
    List<Blog> blogs;
}
