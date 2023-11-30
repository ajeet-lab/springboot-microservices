package com.image.uploadandserve.imageuploadandserve.services;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileHandlerService {
    String fileUploading(String path, MultipartFile file);

    InputStream downloadImage(String path, String fileName) throws FileNotFoundException;
}
