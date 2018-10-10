package com.github.abigail830.wishlist;

import com.github.abigail830.wishlist.util.DefaultEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WishListApplication {

	@Bean(name = "jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		return new DefaultEncryptor();
	}

	public static void main(String[] args) {

		SpringApplication.run(WishListApplication.class, args);
	}
}
