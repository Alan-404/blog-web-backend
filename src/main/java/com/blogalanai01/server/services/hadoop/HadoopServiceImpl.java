package com.blogalanai01.server.services.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogalanai01.server.common.Consts;
@Service
public class HadoopServiceImpl implements HadoopService {
    public boolean createFolder(String folderName){
        try{
            Configuration config = new Configuration();
            config.set(Consts.defaultFS, Consts.hdfsSite);
            FileSystem fs = FileSystem.get(config);
            Path hdfsPath = new Path(folderName);
            fs.mkdirs(hdfsPath);

            fs.close();
            return true;
        }
        catch(Error | IOException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean checkCluster(String folderName){
        try{
            Configuration config = new Configuration();
            config.set(Consts.defaultFS, Consts.hdfsSite);
            FileSystem fs = FileSystem.get(config);
            Path hdfsPath = new Path(Consts.pathPrefix  + folderName);

            boolean path = fs.exists(hdfsPath);

            if(path){
                fs.close();
                return true;
            }

            fs.close();

            
        }
        catch(Error | IOException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveImage(MultipartFile file, String id){
        try{
            Configuration config = new Configuration();
            config.set(Consts.defaultFS, Consts.hdfsSite);
            FileSystem fs = FileSystem.get(config);

            Path hdfsPath = new Path(Consts.pathPrefix + Consts.userPathPrefix + "/" + id + ".jpg");

            FSDataOutputStream outputStream = fs.create(hdfsPath);

            outputStream.write(file.getBytes());

            fs.close();

            return true;
        }
        catch(Error | IOException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public byte[] getAvatarUser(String id){
        try{
            Configuration config = new Configuration();
            config.set(Consts.defaultFS, Consts.hdfsSite);
            FileSystem fs = FileSystem.get(config);

            Path hdfsPath = new Path(Consts.pathPrefix + Consts.userPathPrefix + "/" + id + ".jpg");


            FSDataInputStream inputStream = fs.open(hdfsPath);

            byte[] arrImage = IOUtils.readFullyToByteArray(inputStream);

            fs.close();


            return arrImage;
        }
        catch(Error | IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
