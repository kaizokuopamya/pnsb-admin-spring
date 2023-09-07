package com.itl.pns.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

public class TryMain {
	private Cipher cipher;
	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptorDecryptor.class);

	public static void main(String[] args) {
		String aa = encryptData("Test@123");
		System.out.println(aa);

		System.out.println(decryptData("0n35+iAaGuvxJBCTiZalrg=="));

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
					"AES");
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
