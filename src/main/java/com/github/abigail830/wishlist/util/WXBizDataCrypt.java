package com.github.abigail830.wishlist.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

public class WXBizDataCrypt {

	private String appid;

	private String sessionKey;

	public WXBizDataCrypt(String appid, String sessionKey) {
		this.appid = appid;
		this.sessionKey = sessionKey;
	}


	public String decryptData(String encryptedData, String iv) {

		Map<String, Object> map = new HashMap<>();

		if (StringUtils.length(sessionKey) != 24) {
			map.put("errorCode", "ErrorCode::$IllegalAesKey;");
			return JsonUtil.toJson(map);
		}
		// 对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
		byte[] aesKey = Base64.decodeBase64(sessionKey);

		if (StringUtils.length(iv) != 24) {
			map.put("errorCode", "ErrorCode::$IllegalIv;");
			return JsonUtil.toJson(map);
		}

		// 对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
		byte[] aesIV = Base64.decodeBase64(iv);

		// 对称解密的目标密文为 Base64_Decode(encryptedData)
		byte[] aesCipher = Base64.decodeBase64(encryptedData);


		try {
			byte[] resultByte = AESUtils.decrypt(aesCipher, aesKey, aesIV);

			if (null != resultByte && resultByte.length > 0) {
				String userInfo = new String(resultByte, "UTF-8");
				map.put("code", "0000");
				map.put("msg", "succeed");
				map.put("userInfo", JsonUtil.toObject(userInfo, Map.class));

				// watermark参数说明：
				// 参数  类型  说明
				// watermark   OBJECT  数据水印
				// appid   String  敏感数据归属appid，开发者可校验此参数与自身appid是否一致
				// timestamp   DateInt 敏感数据获取的时间戳, 开发者可以用于数据时效性校验'

				// 根据微信建议：敏感数据归属appid，开发者可校验此参数与自身appid是否一致
				// if decrypted['watermark']['appid'] != self.appId:
				JsonObject jsons = new JsonParser().parse(userInfo).getAsJsonObject();
				String id = jsons.getAsJsonObject("watermark").get("appid").getAsString();
				if (!StringUtils.equals(id, appid)) {
					Map<String, Object> errorMap = new HashMap<>();
					errorMap.put("errorCode", "ErrorCode::$IllegalBuffer;");
					return JsonUtil.toJson(errorMap);
				}
			} else {
				map.put("code", "1000");
				map.put("msg", "false");
			}
		} catch (InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return JsonUtil.toJson(map);
	}

	/**
	 * @param args
	 * @see
	 */
	public static void main(String[] args) {
		String appId = "wx4f4bc4dec97d474b";
		String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
		String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM"
				+ "QmRzooG2xrDcvSnxIMXFufNstNGTyaGS"
				+ "9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+"
				+ "3hVbJSRgv+4lGOETKUQz6OYStslQ142d"
				+ "NCuabNPGBzlooOmB231qMM85d2/fV6Ch"
				+ "evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6"
				+ "/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw"
				+ "u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn"
				+ "/Hz7saL8xz+W//FRAUid1OksQaQx4CMs"
				+ "8LOddcQhULW4ucetDf96JcR3g0gfRK4P"
				+ "C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB"
				+ "6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns"
				+ "/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd"
				+ "lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV"
				+ "oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG"
				+ "20f0a04COwfneQAGGwd5oa+T8yO5hzuy" + "Db/XcxxmK01EpqOyuxINew==";
		String iv = "r7BXXKkLb8qrSNn05n0qiA==";

		WXBizDataCrypt biz = new WXBizDataCrypt(appId, sessionKey);

		System.out.println(biz.decryptData(encryptedData, iv));

	}
}