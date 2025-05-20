package com.lakshan.imagUpload.service;

import com.lakshan.imagUpload.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    File saveFile(MultipartFile file,String name);
    List<File> getAllFiles();
    public String deleteFile(String fileName,long id);

}
