package trandafyl.dev.hackathontest.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import trandafyl.dev.hackathontest.services.S3Service;

@RestController
@AllArgsConstructor
public class S3Controller {
    private final S3Service service;

    @PostMapping("/upload/")
    public String uploadFile(@RequestParam(value="file") MultipartFile file){
        return service.uploadFile(file);
    }
}
