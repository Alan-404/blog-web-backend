package com.blogalanai01.server.dtos.blog;

import java.util.List;

import com.blogalanai01.server.dtos.comment.InfoCommentDTO;
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
public class ShowBlogDTO {
    private Blog blog = null;
    private User author;
    private List<InfoCommentDTO> comments;
}
