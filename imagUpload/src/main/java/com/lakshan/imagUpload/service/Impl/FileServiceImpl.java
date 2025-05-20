package com.lakshan.imagUpload.service.Impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.lakshan.imagUpload.entity.File;
import com.lakshan.imagUpload.repo.FileRepo;
import com.lakshan.imagUpload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepo fileRepo;

    @Value("S{aws.access_key}")
    private String access_key;
    @Value("${aws.secret}")
    private String secret_key;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.bucket_name}")
    private String bkt_name;

    @Override
    public File saveFile(MultipartFile file, String name) {
        String saveFileURL = saveFileToAWS(file,name);
        File fileToSave = File.builder().fileUrl(saveFileURL).name(name).build();
        return fileRepo.save(fileToSave);
    }

    @Override
    public List<File> getAllFiles() {
        return fileRepo.findAll();
    }

    private String saveFileToAWS(MultipartFile file,String name) {
        try {
            String s3FileName = UUID.randomUUID().toString() + "_" + name;

            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(access_key, secret_key);

            AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(region)
                    .build();

            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(bkt_name, s3FileName, inputStream, objectMetadata);
            amazonS3.putObject(putObjectRequest);

            return "https://" + bkt_name + ".s3.amazonaws.com/" + s3FileName;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());

        }
    }

    public String deleteFile(String fileName,long id) {
        String deleteFromS3 = deleteFileFromAWS(fileName);
        fileRepo.deleteById(id);
        return deleteFromS3;
    }
    private String deleteFileFromAWS(String fileName) {
        try {
            // Initialize AWS credentials
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials("AKIAYEKP5V44Z4MPZPRM", secret_key);

            // Create AmazonS3 client with credentials and region
            AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(region)
                    .build();

            // Delete object from S3 using the bucket name and file name (key)
            amazonS3.deleteObject(bkt_name, fileName);
            System.out.println("File deleted successfully from S3.");
            return "OK";


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete file from S3: " + e.getMessage());
        }
    }

}
