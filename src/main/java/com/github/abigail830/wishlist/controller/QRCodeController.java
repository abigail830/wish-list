package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.dto.v1.QRCodeRequestDTO;
import com.github.abigail830.wishlist.service.QRCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

    @Autowired
    QRCodeService qrCodeService;

    @PostMapping
    public ResponseEntity<byte[]> getQRCodeForContent(
            @RequestBody QRCodeRequestDTO qrCodeRequestDTO) {

        final byte[] result = qrCodeService.generate(qrCodeRequestDTO.getPage(),
                qrCodeRequestDTO.getScene(), qrCodeRequestDTO.getWidth());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(result);

    }

}
