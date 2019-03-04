package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.service.QRCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

    @Autowired
    QRCodeService qrCodeService;

    @GetMapping(value = "/limit", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQRCodeForContent(
            @RequestParam String page) {

        final byte[] result = qrCodeService.generate(page);
        HttpHeaders headers_1 = new HttpHeaders();
        headers_1.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(result, headers_1, HttpStatus.CREATED);

    }

}
