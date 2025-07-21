package com.submitIo.service.mediaService;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.submitIo.responseDto.PresignedUrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

     private final AmazonS3 s3Client;

     @Value("${cloud.aws.s3.bucket}")
     private String bucketName;

     public ResponseEntity<String> uploadImage(MultipartFile image,String fileNameToBeSavedWith){
//         String fileName = image.getOriginalFilename();
         if(image == null){
             return new ResponseEntity<>("Image is null", HttpStatus.BAD_REQUEST);
         }
         ObjectMetadata metadata = new ObjectMetadata();
         metadata.setContentType(image.getContentType());
         metadata.setContentLength(image.getSize());
         PutObjectResult putObjectResult;
         try {
             putObjectResult = s3Client.putObject(bucketName, fileNameToBeSavedWith, image.getInputStream(), metadata);
         } catch (IOException e) {
             return new ResponseEntity<>("Error uploading image"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
         }
         return new ResponseEntity<>("Image uploaded successfully: "+putObjectResult, HttpStatus.OK);
//         return "https://submitdotio.s3.ap-south-1.amazonaws.com/" + fileNameToBeSavedWith;
     }

     public ResponseEntity<PresignedUrlResponseDto>  presignedUrl(String fileName){

         Date expirationDate = new Date();
         int hours = 2;
         expirationDate.setTime(expirationDate.getTime() + hours *  1000 * 60 * 60);

         GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName);
         request.withMethod(HttpMethod.GET);
         request.setExpiration(expirationDate);
         URL url = s3Client.generatePresignedUrl(request);
         PresignedUrlResponseDto presignedUrlResponseDto = new PresignedUrlResponseDto();
         presignedUrlResponseDto.setPresignedUrl(url.toString());
         presignedUrlResponseDto.setFilename(fileName);
         return new ResponseEntity<>(presignedUrlResponseDto,HttpStatus.OK);
     }

     public ResponseEntity<PresignedUrlResponseDto> getObjectUrlByName(String fileName){
         S3Object object=s3Client.getObject(bucketName,fileName);
         String key=object.getKey();
         return presignedUrl(key);
     }
}
