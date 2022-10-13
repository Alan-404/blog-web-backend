package com.blogalanai01.server.dtos.comment;

import com.blogalanai01.server.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoCommentDTO {
    private String id;
    private String blogId;
    private String content;
    private String reply;
    private User user;
    private int numReplies;
}
