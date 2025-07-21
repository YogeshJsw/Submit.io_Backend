package com.submitIo.responseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PresignedUrlResponseDto {

    private String filename;
    private String presignedUrl;
}
