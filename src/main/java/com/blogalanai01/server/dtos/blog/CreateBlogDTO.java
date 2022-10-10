package com.blogalanai01.server.dtos.blog;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.blogalanai01.server.enums.BlogState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBlogDTO {
    private List<String> categoryId;
    private String userId;
    private String title;
    private String intro;
    private String content;
    private MultipartFile thumnail;
    private BlogState state;
    

    public void setBlogState(BlogState state){
        this.state = state;
    }

    public BlogState getBlogState(){
        return this.state;
    }
}
