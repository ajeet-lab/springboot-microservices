package com.ajeet.blog.ajeetblog.servicesImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ajeet.blog.ajeetblog.services.FileService;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {      
            String name = file.getOriginalFilename().replace("_", "");

            if(name == null || name == ""){
                return "Error: File should not be empty";
            }

        String randomId = UUID.randomUUID().toString();
        int lastIndexOfExt = name.lastIndexOf(".");
        String fileName1 = name.substring(0, lastIndexOfExt) + "_"
                + randomId.concat(name.substring(lastIndexOfExt)).toString();

        String filePath = path + File.separator + fileName1;

        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
            Files.copy(file.getInputStream(), Paths.get(filePath));
            return fileName1;
            
    }

    @Override
    public InputStream getImagInputStream(String imageName, String path) throws FileNotFoundException {
        String filePath = path+File.separator+imageName;
        InputStream is = new FileInputStream(filePath);
       return is;
    }
    
}
