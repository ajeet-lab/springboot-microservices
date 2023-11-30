package com.image.uploadandserve.imageuploadandserve.servicesImp;

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

import com.image.uploadandserve.imageuploadandserve.services.FileHandlerService;

@Service
public class FileHandlerServiceImpl implements FileHandlerService {

    @Override
    public String fileUploading(String path, MultipartFile file) {
        
        try {
            String name = file.getOriginalFilename().replace("_", "");

            if(name == null || name == ""){
                return "Error: File should not be empty";
            }
        // Save file with Orginal file name
        // String filePath = path+File.separator+name;

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
            return name;
        } catch (IOException e) {
            return "Error: "+e.getMessage();
        }       
    }

    @Override
    public InputStream downloadImage(String path, String fileName) throws FileNotFoundException {
       String filePath = path+File.separator+fileName;
       InputStream is = new FileInputStream(filePath);
       return is;
    }

}
