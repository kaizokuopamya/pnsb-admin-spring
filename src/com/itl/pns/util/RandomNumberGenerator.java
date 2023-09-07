package com.itl.pns.util;

import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.itl.pns.constant.ApplicationConstants;

public class RandomNumberGenerator {

	private static final Logger logger = LogManager.getLogger(RandomNumberGenerator.class);

	public static String getRandomUUID() {
		StringBuilder randomNum = new StringBuilder();
		Random randomGenerator = new Random();
		for (int idx = 1; idx <= 4; ++idx) {
			int randomInt = randomGenerator.nextInt(10000);
			String rndNum = String.valueOf(randomInt);
			randomNum.append(rndNum);
			if (idx < 4)
				randomNum.append("-");
		}
		return randomNum.toString();
	}

	public String generateRandomString() {

		int noOfCAPSAlpha = 1;
		int noOfDigits = 1;
		int noOfSplChars = 1;
		int minLen = 8;
		int maxLen = 12;

		char[] pswd = RandomNumberGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars);

		return new String(pswd);
	}

	public static char[] generatePswd(int minLen, int maxLen, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars) {
		if (minLen > maxLen)
			throw new IllegalArgumentException("Min. Length > Max. Length!");
		if ((noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen)
			throw new IllegalArgumentException(
					"Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
		Random rnd = new Random();
		int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
		char[] pswd = new char[len];
		int index = 0;
		for (int i = 0; i < noOfCAPSAlpha; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = ApplicationConstants.ALPHA_CAPS.charAt(rnd.nextInt(ApplicationConstants.ALPHA_CAPS.length()));
		}
		for (int i = 0; i < noOfDigits; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = ApplicationConstants.NUM.charAt(rnd.nextInt(ApplicationConstants.NUM.length()));
		}
		for (int i = 0; i < noOfSplChars; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = ApplicationConstants.SPL_CHARS.charAt(rnd.nextInt(ApplicationConstants.SPL_CHARS.length()));
		}
		for (int i = 0; i < len; i++) {
			if (pswd[i] == 0) {
				pswd[i] = ApplicationConstants.ALPHA.charAt(rnd.nextInt(ApplicationConstants.ALPHA.length()));
			}
		}
		return pswd;
	}

	private static int getNextIndex(Random rnd, int len, char[] pswd) {
		int index = rnd.nextInt(len);
		while (pswd[index = rnd.nextInt(len)] != 0)
			;
		return index;
	}

	public int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(ApplicationConstants.CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}

	public static final String getRandomString(int targetStringLength) {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();

	}

	public static String generateActivationCode() {
		return "111111";
		
//		int m = (int) Math.pow(10, 6 - 1);
//		int otp=m + new Random().nextInt(9 * m);
//		return otp+"";
	}

	public static String generateRandomActivationCode() {
		String strOtp = "" + ((int) ((Math.random() * (111111 - 999999)) * (-1)));
		return strOtp.replaceAll("0", "1");
	}

	public String generateRandomNumber() {

		String randNo = "" + ((int) ((Math.random() * (111111111 - 999999999)) * (-1)));
		logger.info("Random Number: " + randNo);
		return randNo;
	}

	public String generateRandomStringForOoenKmDoc() {

		int noOfCAPSAlpha = 1;
		int noOfDigits = 1;
		int noOfSplChars = 1;
		int minLen = 35;
		int maxLen = 40;

		char[] pswd = RandomNumberGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars);

		return new String(pswd);
	}

	public static final int onePad(String value, int size) {
		String s = "1111111111111111" + value;
		return Integer.parseInt(s.substring(s.length() - size));
	}

	public static String generateOtpCode() {
		String strOtp = "" + (int) (((Math.random() * (111111 - 999999)) * (-1)));
		return strOtp.replaceAll("0", "1");
	}
	
	public static void main(String[] args) {
		int m = (int) Math.pow(10, 6 - 1);		
		int otp=m + new Random().nextInt(9 * m);
//		return otp+"";
	}

}