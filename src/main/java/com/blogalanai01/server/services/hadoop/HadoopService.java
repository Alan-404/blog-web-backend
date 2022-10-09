package com.blogalanai01.server.services.hadoop;

import org.springframework.web.multipart.MultipartFile;

public interface HadoopService {
    public boolean createFolder(String folderName);
    public boolean checkCluster(String folderName);
    public boolean saveImage(MultipartFile file, String id, String clusterName);
    public byte[] getImage(String id, String clusterName);
}
