package com.github.abigail830.wishlist.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;


/**
 * @author Awoke
 * @version 2018-1-24
 * @see AESUtils
 */
public class AESUtils {
	public static boolean initialized = false;

	/**
	 * AES解密
	 *
	 * @param content 密文
	 * @return
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchProviderException
	 */
	public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte)
			throws InvalidAlgorithmParameterException {
		initialize();
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(keyByte, "AES");

			cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化
			return cipher.doFinal(content);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| InvalidKeyException | NoSuchProviderException | BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.fillInStackTrace();
			e.printStackTrace();
		}
		return null;
	}

	public static void initialize() {
		if (initialized) return;
		Security.addProvider(new BouncyCastleProvider());
		initialized = true;
	}

	/**
	 * 生成iv
	 *
	 * @param iv
	 * @return
	 * @throws Exception
	 * @see
	 */
	public static AlgorithmParameters generateIV(byte[] iv)
			throws Exception {
		AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
		params.init(new IvParameterSpec(iv));
		return params;
	}

}