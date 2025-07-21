package com.submitIo.api.u;

import com.submitIo.service.GmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gmail")
@RequiredArgsConstructor
public class GmailController {

    private final GmailService gmailService;

    @GetMapping("/sendOtp")
    public ResponseEntity<String> sendOtp(String userEmailToSendTo){
        return gmailService.sendOtp(userEmailToSendTo);
    }
}
