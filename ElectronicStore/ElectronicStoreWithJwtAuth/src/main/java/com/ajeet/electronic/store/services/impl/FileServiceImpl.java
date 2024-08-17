package com.ajeet.electronic.store.services.impl;

import com.ajeet.electronic.store.exceptions.BadApiRequest;
import com.ajeet.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);



    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFile = file.getOriginalFilename();
        assert originalFile != null;
        String uploadRandomString = UUID.randomUUID().toString();
        String extension = originalFile.substring(originalFile.lastIndexOf("."));
        String newFileName = originalFile.substring(0, originalFile.lastIndexOf("."))+"_"+uploadRandomString.concat(uploadRandomString) ;
        String fileNameWithExtention = newFileName+extension;
        String fullPathWithFileName = path+File.separator+fileNameWithExtention;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")){

            // Check folder exists or not, if not, create a folder with given path
            File folder = new File(path);
            if(!folder.exists()){
                logger.info("Folder does not exists !!");
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtention;

        }else{
            throw new BadApiRequest("File with this "+extension+" extension is not allowed !!");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPathWithFileName= path+File.separator+name;
        return new FileInputStream(fullPathWithFileName);
    }
}
