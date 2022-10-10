package com.blogalanai01.server.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogalanai01.server.dtos.hadoop.CreateClusterDTO;
import com.blogalanai01.server.dtos.hadoop.CreateFileDTO;
import com.blogalanai01.server.services.hadoop.HadoopService;

@RestController
@RequestMapping("/hadoop")
public class HadoopController {
    private final HadoopService hadoopService;

    public HadoopController(HadoopService hadoopService){
        this.hadoopService = hadoopService;
    }

    @PostMapping("/api")
    public boolean createCluster(@RequestBody CreateClusterDTO clusterDTO){
        return this.hadoopService.createFolder(clusterDTO.getClusterName());
    }

    @PostMapping("/image")
    public boolean uploadImage(@ModelAttribute CreateFileDTO createFileDTO){
        return this.hadoopService.saveImage(createFileDTO.getFile(), createFileDTO.getId(), "/blogs/image");
    }

    @DeleteMapping("/image/{id}")
    public boolean deleteImage(@PathVariable("id") String id){
        return this.hadoopService.deleteImage(id, "blogs/image");
    }

}
