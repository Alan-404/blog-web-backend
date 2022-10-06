package com.blogalanai01.server.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogalanai01.server.dtos.hadoop.CreateClusterDTO;
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
}
