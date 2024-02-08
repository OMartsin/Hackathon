package trandafyl.dev.hackathontest.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3Service {
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    private S3Service(AmazonS3 s3Client){
        this.s3Client =s3Client;
    }

    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultyPartFileToFile(file);
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, filename, fileObj));
        fileObj.delete();
        return filename;
    }

    public void deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
    }

    public String createURL (String fileName){
        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }

    private File convertMultyPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try(var fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        } catch(IOException e) {

        }
        return convertedFile;
    }
}
