package com.wumin.boot152.common.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

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
}
