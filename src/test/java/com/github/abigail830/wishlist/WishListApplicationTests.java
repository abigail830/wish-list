package com.github.abigail830.wishlist;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WishListApplicationTests {

	@Autowired
	StringEncryptor encryptor;

//	@Test
	public void getPass() {
		String password = encryptor.encrypt("Encrypted password");
		System.out.println(password+"----------------");
		Assert.assertTrue(password.length() > 0);



	}

}
