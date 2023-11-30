package com.image.uploadandserve.imageuploadandserve.payloads;

public class ApiResponse {
  private  String fileName;
  private String message;
  private Boolean isSuccess;

    

    public ApiResponse() {
    }
    public ApiResponse(String fileName, String message, Boolean isSuccess) {
        this.fileName = fileName;
        this.message = message;
        this.isSuccess = isSuccess;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Boolean getIsSuccess() {
        return isSuccess;
    }
    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    
}
