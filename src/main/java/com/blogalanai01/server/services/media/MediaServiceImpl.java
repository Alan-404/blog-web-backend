package com.blogalanai01.server.services.media;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogalanai01.server.common.Consts;


@Service
public class MediaServiceImpl implements MediaService {
    public boolean saveMedia(MultipartFile file, String name , String path){
        try{
            String typeFile = file.getContentType().split("/")[1];
            file.transferTo(new File(Consts.localPathPrefix + path + "/" + name + "." + typeFile));
            return true;
        }
        catch(Error | IllegalStateException | IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
