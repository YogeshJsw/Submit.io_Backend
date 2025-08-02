package com.submitIo.api.r;

import com.submitIo.responseDto.PresignedUrlResponseDto;
import com.submitIo.service.mediaService.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/aws")
@RequiredArgsConstructor
public class AwsS3Api {

    private final ImageUploadService imageUploadService;

    @PostMapping("/upload/image")
    public ResponseEntity<String> uploadImageToS3(@RequestParam MultipartFile file, @RequestParam String fileNameToBeSavedWith) {
        return imageUploadService.uploadImage(file,fileNameToBeSavedWith);
    }

    @GetMapping("/s3/presignedUrl/{fileName}")
    public ResponseEntity<PresignedUrlResponseDto> getPresignedUrl(@PathVariable String fileName){
        return imageUploadService.getObjectUrlByName(fileName);
    }
}
