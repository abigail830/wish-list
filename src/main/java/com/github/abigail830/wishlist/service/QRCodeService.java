package com.github.abigail830.wishlist.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Optional;

@Service
@Slf4j
public class QRCodeService {

    public Optional<BufferedImage> generate(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        try {
            log.info("Creating QR Code for {}", content);
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
            return Optional.ofNullable(MatrixToImageWriter.toBufferedImage(bitMatrix));
        } catch (WriterException e) {
            e.printStackTrace();
            log.error("fail to generate QR Code for {}", content);
            return Optional.empty();
        }
    }

}
