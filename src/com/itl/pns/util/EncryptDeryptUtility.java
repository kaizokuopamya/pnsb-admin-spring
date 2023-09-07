package com.itl.pns.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.itl.pns.bean.ResponseMessageBean;

import antlr.collections.List;

public class EncryptDeryptUtility {

	private static final Logger logger = LoggerFactory
			.getLogger(EncryptorDecryptor.class);

	public EncryptDeryptUtility() {

	}

	public byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public  SecretKey generateKeyFromPassword(String password, byte[] saltBytes) throws GeneralSecurityException {
		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), saltBytes, 1000, 256);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		return new SecretKeySpec(secretKey.getEncoded(), "AES");
	}

	public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		return new String(bytes);
	}

	public static String md5(String original) {
		byte[] digest = DigestUtils.md5(original);
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);
		StringBuilder sb = new StringBuilder();
		// Now we need to zero pad it if you actually want the full 32 chars.
		while (hashtext.length() < 32) {
			sb.delete(0, sb.length());
			sb.append("0");
			sb.append(hashtext);
			hashtext = sb.toString();
		}

		return hashtext;
	}

	public static String decryptNonAndroid(String str, String myKey) {

		String decrypted = null;
		try {
			String salt = "";
			String iv = "";
			String encryptedText = "";

			if (str.contains("~~~")) {
				salt = str.split("~~~")[0].replaceAll("\"", "");
				iv = str.split("~~~")[1].replaceAll("\"", "");
				encryptedText = str.split("~~~")[2].replaceAll("\"", "");
				EncryptorDecryptor dec = new EncryptorDecryptor(256, 1000);
				decrypted = dec.decrypt(salt, iv, myKey, encryptedText);
			} else if (str.contains(" ")) {
				salt = str.split(" ")[0].replaceAll("\"", "");
				iv = str.split(" ")[1].replaceAll("\"", "");
				encryptedText = str.split(" ")[2].replaceAll("\"", "");
				EncryptorDecryptor dec = new EncryptorDecryptor(256, 1000);
				decrypted = dec.decrypt(salt, iv, myKey, encryptedText);
			} else {
				decrypted = str;
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		decrypted = stripXSS(decrypted.replaceAll("&amp", "")
				.replaceAll("&lt", "").replaceAll("&gt", "")
				.replaceAll("&quot", "").replaceAll("&#x27", "")
				.replaceAll("&#x2F", ""));
		return decrypted;
	}

	public  String encryptNonAndroid(String txtToEncrypt, String passphrase) {

		String combineData = "";
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			String saltHex = getRandomHexString(32);
			String ivHex = getRandomHexString(32);
			byte[] salt = hexStringToByteArray(saltHex);
			byte[] iv = hexStringToByteArray(ivHex);
			SecretKeySpec sKey = (SecretKeySpec) generateKeyFromPassword(passphrase, salt);
			cipher.init(Cipher.ENCRYPT_MODE, sKey, new IvParameterSpec(iv));
			byte[] utf8 = txtToEncrypt.getBytes("UTF-8");
			byte[] enc = cipher.doFinal(utf8);
			combineData = saltHex + " " + ivHex + " " + new sun.misc.BASE64Encoder().encode(enc);
		} catch (Exception e) {
			logger.error("Unable to encryptMaster : " + e);
			return null;
		}
		return combineData;
	}

	public static String stripXSS(String value) {
		if (value != null) {
		
			value = value.replaceAll("", "");

			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>",
					Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid anything in a src='...' type of expression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>",
					Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:",
					Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:",
					Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
		}
		return value;
	}

	private String getRandomHexString(int numchars) {
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		while (sb.length() < numchars) {
			sb.append(Integer.toHexString(r.nextInt()));
		}
		return sb.toString().substring(0, numchars);
	}
	public static String clobStringConversion(Clob clb) throws IOException, SQLException {
	       if (clb == null)
	         return "";
	        StringBuffer str = new StringBuffer();
	        String strng;
	        BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());
	        while ((strng = bufferRead.readLine()) != null)
	         str.append(strng);
	         return str.toString();
	}
	
	// Encryption start
	public static String getEncryptedResponse(Gson gson, ResponseMessageBean obj) {
		String jsonToOriginator;
		jsonToOriginator = gson.toJson(obj);
		return jsonToOriginator;
	}
	
	
	/**
	   * encrypt value using cryptojs
	   * @PASSPHRASE
	   * @txtToDecrypt
	   */
//	  encryptText(PASSPHRASE, txtToEncrypt) {
//
//	    var salt = CryptoJS.lib.WordArray.random(256 / 16);
//	    var iv = CryptoJS.lib.WordArray.random(256 / 16);
//	    var key128Bits = CryptoJS.PBKDF2(PASSPHRASE, salt, { keySize: 256 / 32 });
//	    var key128Bits100Iterations = CryptoJS.PBKDF2(PASSPHRASE, salt, { keySize: 256 / 32, iterations: 1000 });
//	    var encrypted = CryptoJS.AES.encrypt(txtToEncrypt, key128Bits100Iterations, { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
//	    var CombineData = salt + " " + iv + " " + encrypted;
//	    console.log('SERVICE REQUEST ', txtToEncrypt);
//	    console.log('PASSPHRASE: ', PASSPHRASE);
//	    return CombineData;
//	  }


	
	public static void main(String[] args) {			
//		try {
//			System.out.println(PasswordSecurityUtil.toHexString(PasswordSecurityUtil.getSHA("4nwTy$cf")));
//		} catch (NoSuchAlgorithmException e) {
//			 TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(EncryptorDecryptor.decryptDataForLangJson("a2403169a61eb69aa3a347e3b427331e 54dcf7e6c6c2ed1f382c68f8baa8cadf n+qy7IJy6PTSwxWuz4PAN4nv0Y43VSVCw7uCeTZaVuU="));
//		System.out.println(EncryptorDecryptor.encryptData("ashish.yadav"));
//		System.out.println(EncryptorDecryptor.decryptData("xDTkbmfsAFz2RXMOoS5yPQ=="));
		System.out.println(EncryptorDecryptor.encryptData("deep2706"));
//		System.out.println(EncryptorDecryptor.encryptData("prathmeshM"));
//		System.out.println(EncryptDeryptUtility.decryptNonAndroid("8776f108e247ab1e2b323042c049c266407c81fbad41bde1e8dfc1bb66fd267e", "@MrN$2Qi8R"));
//		System.out.println(EncryptDeryptUtility.md5("Test@123"));
		
//		System.out.println(EncryptorDecryptor.encryptData("rajesh"));
		
	}

}