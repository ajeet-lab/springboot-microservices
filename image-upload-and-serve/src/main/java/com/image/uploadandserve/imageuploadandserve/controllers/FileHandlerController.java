package com.image.uploadandserve.imageuploadandserve.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.image.uploadandserve.imageuploadandserve.payloads.ApiResponse;
import com.image.uploadandserve.imageuploadandserve.services.FileHandlerService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class FileHandlerController {


    @Autowired
    private FileHandlerService fileHandlerService;

    @Value("${project.image}")
    private String path;
    
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadImages(@RequestParam("image") MultipartFile file){
        String fileName = this.fileHandlerService.fileUploading(path, file);
        ApiResponse apiResponse = new ApiResponse(fileName, "File uploaded successfully", true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    } 


    @GetMapping("/image/{imageName}")
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{

        InputStream resource = this.fileHandlerService.downloadImage(path, imageName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

    }
}
