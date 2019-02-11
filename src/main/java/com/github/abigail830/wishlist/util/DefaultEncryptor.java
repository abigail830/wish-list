package com.github.abigail830.wishlist.util;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class DefaultEncryptor implements StringEncryptor {

	private final StringEncryptor encryptor;

	public DefaultEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(System.getenv("ENCRYPTION_KEY_WL"));
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("10000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("base64");
		encryptor.setConfig(config);
		this.encryptor = encryptor;
	}

	@Override
	public String encrypt(String message) {
		return encryptor.encrypt(message);
	}

	@Override
	public String decrypt(String encryptedMessage) {
		return encryptor.decrypt(encryptedMessage);
	}

}