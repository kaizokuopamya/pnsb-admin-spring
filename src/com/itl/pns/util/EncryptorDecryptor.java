
package com.itl.pns.util;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

public class EncryptorDecryptor {

	private Cipher cipher;
	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptorDecryptor.class);

	public EncryptorDecryptor(int keySize, int EncryptorDecryptor) {
		super();
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public String decrypt1(String salt, String ivparam, String passphrase, String EncryptedText) {
		String decryptedValue = null;
		try {
			// byte[] saltBytes = hexStringToByteArray(salt);
			byte[] saltBytes = salt.getBytes();
			SecretKeySpec sKey = (SecretKeySpec) generateKeyFromPassword(passphrase, saltBytes);
			IvParameterSpec iv = new IvParameterSpec(ivparam.getBytes("UTF-8"));
			cipher.init(Cipher.DECRYPT_MODE, sKey, iv);
			byte[] decordedValue = new BASE64Decoder().decodeBuffer(EncryptedText);
			byte[] decValue = cipher.doFinal(decordedValue);
			decryptedValue = new String(decValue);

		} catch (Exception e) {
			decryptedValue = "97";
			LOGGER.error("Exception in decrypt : ", e);
		}
		return decryptedValue;

	}

	public String decrypt(String salt, String iv, String passphrase, String EncryptedText) {
		String decryptedValue = null;
		try {
			byte[] saltBytes = hexStringToByteArray(salt);
			SecretKeySpec sKey = (SecretKeySpec) generateKeyFromPassword(passphrase, saltBytes);
			byte[] ivBytes = hexStringToByteArray(iv);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
			cipher.init(Cipher.DECRYPT_MODE, sKey, ivParameterSpec);
			byte[] decordedValue = new BASE64Decoder().decodeBuffer(EncryptedText);
			byte[] decValue = cipher.doFinal(decordedValue);
			decryptedValue = new String(decValue);

		} catch (Exception e) {
			decryptedValue = "97";
			LOGGER.error("Exception in decrypt : ", e);
		}
		return decryptedValue;

	}

	public static SecretKey generateKeyFromPassword(String password, byte[] saltBytes) throws GeneralSecurityException {

		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), saltBytes, 1000, 256);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		return new SecretKeySpec(secretKey.getEncoded(), "AES");
	}

	public static byte[] hexStringToByteArray(String s) {

		int len = s.length();
		byte[] data = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}

		return data;

	}

	public static String encryptData(String text) {
		try {
			SecretKeySpec spec = new SecretKeySpec(GlobalPropertyReader.getInstance().getValue("DATA_ENCR").getBytes(),
					"AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(1, spec);

			String output = new sun.misc.BASE64Encoder().encode(cipher.doFinal(text.getBytes()));

			return output;
		} catch (Exception e) {
			LOGGER.error("Exception in encrypt : ", e);

		}
		return null;
	}
	
	
		public static String decryptData(String enc) {
		try {
			SecretKeySpec spec = new SecretKeySpec(GlobalPropertyReader.getInstance().getValue("DATA_ENCR").getBytes(),
//			SecretKeySpec spec = new SecretKeySpec("jrD@Mt6i#0mnip$b".getBytes(),
					"AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(2, spec);

			byte[] original = cipher.doFinal(new BASE64Decoder().decodeBuffer(enc));
			return new String(original);
		} catch (Exception e) {
			LOGGER.error("Exception in decrypt : ", e);
		}
		return "";
	}

	public static String encryptDataForLangJson(String text) {
		try {
			SecretKeySpec spec = new SecretKeySpec(
					GlobalPropertyReader.getInstance().getValue("LANG_DATA_ENCR").getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(1, spec);

			String output = new sun.misc.BASE64Encoder().encode(cipher.doFinal(text.getBytes()));

			return output;
		} catch (Exception e) {
			LOGGER.error("Exception in encrypt : ", e);

		}
		return null;
	}

	public static String decryptDataForLangJson(String enc) {
		try {
			SecretKeySpec spec = new SecretKeySpec(
					GlobalPropertyReader.getInstance().getValue("LANG_DATA_ENCR").getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(2, spec);

			byte[] original = cipher.doFinal(new BASE64Decoder().decodeBuffer(enc));
			return new String(original);
		} catch (Exception e) {
			LOGGER.error("Exception in decrypt : ", e);
		}
		return null;
	}
}