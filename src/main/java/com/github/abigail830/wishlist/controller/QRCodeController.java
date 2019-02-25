package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.dto.v1.QRCodeRequestDTO;
import com.github.abigail830.wishlist.service.QRCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

    @Autowired
    QRCodeService qrCodeService;

    @PostMapping
    public ResponseEntity<byte[]> getQRCodeForContent(
            @RequestBody QRCodeRequestDTO qrCodeRequestDTO) {

        try {
            final Optional<BufferedImage> qrCode = qrCodeService.generate(qrCodeRequestDTO.getContent(),
                    qrCodeRequestDTO.getWidth(),
                    qrCodeRequestDTO.getHeight());

            if (qrCode.isPresent()) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(qrCode.get(), "png", outputStream);
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(outputStream.toByteArray());
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }


    }

}
