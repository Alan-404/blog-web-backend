package com.blogalanai01.server.dtos.hadoop;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFileDTO {
    private MultipartFile file;
    private String id;
}
