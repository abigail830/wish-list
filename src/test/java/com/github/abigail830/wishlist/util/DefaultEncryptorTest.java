package com.github.abigail830.wishlist.util;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DefaultEncryptorTest {

    DefaultEncryptor encryptor;
    @Before
    public void setUp() throws Exception {
        encryptor = new DefaultEncryptor();
    }

    @Test
    public void encrypt() {
        String pwd = encryptor.encrypt("Wishlist20181008");
        System.out.println(pwd);
    }

    @Test
    public void decrypt() {
        String pwd = "lNUcvnJHALkQdC51gY+bWE7UJ0ar7b21LW3HTNftCPY=";
        System.out.println(encryptor.decrypt(pwd));
    }
}