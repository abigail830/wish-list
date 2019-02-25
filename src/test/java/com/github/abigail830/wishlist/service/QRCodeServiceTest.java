package com.github.abigail830.wishlist.service;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class QRCodeServiceTest {

    QRCodeService qrCodeService;

    @Before
    public void setUp() throws Exception {
        qrCodeService = new QRCodeService();
    }

    @Test
    public void generate() {
        final Optional<BufferedImage> result = qrCodeService.generate("http:/www.baidu.com", 100, 100);
        assertTrue(result.isPresent());

//        try {
//            File outputfile = new File("qr_code.png");
//            ImageIO.write(result.get(), "png", outputfile);
//        } catch (IOException e) {
//            // handle exception
//        }
    }
}