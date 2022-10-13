package com.blogalanai01.server.services.media;

import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    public boolean saveMedia(MultipartFile file, String name, String path);
}
