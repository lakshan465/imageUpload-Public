package com.lakshan.imagUpload.api;

import com.lakshan.imagUpload.entity.File;
import com.lakshan.imagUpload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<Object> saveFile(@RequestParam MultipartFile file,
                                           @RequestParam String name) {
        System.out.println(" @PostMapping(\"/upload\")");
        if (file.isEmpty() || name.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File and name cannot be empty");
        }
        return ResponseEntity.ok(fileService.saveFile(file, name));
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<File>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String fileS3Name,@RequestParam long id) {
        return ResponseEntity.ok(fileService.deleteFile(fileS3Name,id));
    }

}
