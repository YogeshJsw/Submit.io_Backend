package com.submitIo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GmailService {

    private final JavaMailSender gmailSender;
    private final String ADMIN_GMAIL_ID="submitdotio@gmail.com";
    private String last_OTP;
    private Instant otpTimeStamp;

    public ResponseEntity<String> sendOtp(String userEmailToSendTo){
        String otp=String.format("%05d", new Random().nextInt(100000));
        last_OTP=otp;
        otpTimeStamp=Instant.now();

        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(ADMIN_GMAIL_ID);
        message.setTo(userEmailToSendTo);
        message.setSubject("Submit.io OTP verification");
        message.setText("You OTP for verification: "+otp);
        gmailSender.send(message);
        return new ResponseEntity<>("OTP sent", HttpStatus.OK);
    }
}
