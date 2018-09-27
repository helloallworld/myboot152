package com.wumin.boot152.common.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtils {
	/**
	 * @Title: generateSalt
	 * @Description: TODO(获取密码盐)
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws
	 */
	public static String generateSalt() {
		SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
		String hex = secureRandom.nextBytes().toHex();
		return hex;
	}

	/**
	 * @Title: getPassword
	 * @Description: TODO(获取加密后的密文)
	 * @param @param password 原始密码
	 * @param @param salt 密码盐
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws
	 */
	public static String getPassword(String password, String salt) {
		SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
		String hashedPasswordBase64 = new Sha256Hash(password, salt, 1024).toBase64();
		return hashedPasswordBase64;
	}

	/**
	 * 加密模式
	 * @param key
	 * @return
	 */
	public static SecretKeySpec getKey(String key) {
		//返回生成指定算法密钥生成器的 KeyGenerator 对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance("AES");
			//AES 要求密钥长度为 128
			kg.init(128, new SecureRandom(key.getBytes()));
			//生成一个密钥
			SecretKey secretKey = kg.generateKey();
			return  new SecretKeySpec(secretKey.getEncoded(), "AES");// 转换为AES专用密钥
		} catch (NoSuchAlgorithmException ex) {

		}
		return null;
	}
}
