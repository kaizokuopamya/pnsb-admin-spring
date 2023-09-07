package com.itl.pns.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.itl.pns.bean.ResponseMessageBean;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Utils {

	private static Logger LOGGER = Logger.getLogger(Utils.class);
	int keySize;
	int iterationCount;
	Cipher cipher;
	static InputStream in = null;

	public static String toHex(byte[] data) {
		return toHex(data, data.length);
	}

	public static String generateOTPCode(int length) {
		String otp = String.valueOf((int) ((Math.random() * (100000000 - 999999999)) * (-1)));
		if (otp.length() > length) {
			otp = otp.substring(0, length);
		} else {
			otp = zeroPadRight(otp, length);
		}
		return otp;
	}

	public static String toHex(byte[] data, int length) {
		String digits = "0123456789abcdef";
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i != length; i++) {
			int v = data[i] & 0xff;
			buf.append(digits.charAt(v >> 4));
			buf.append(digits.charAt(v & 0xf));
		}
		return buf.toString();
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static boolean validatePIN(String pin) {
		if (!(pin.length() == 4))
			return false;
		else {
			try {
				Integer.parseInt(pin);
				return true;
			} catch (Exception ex) {
				LOGGER.info("Exception  " + ex);
				return false;
			}
		}

	}

	public static String exceptionToString(Exception ex) {
		StringWriter errors = new StringWriter();
		LOGGER.info(new PrintWriter(errors));
		return errors.toString();
	}

	public static String generateActivationCode() {

		String strOtp = "" + (int) (((Math.random() * (111111 - 999999)) * (-1)));
		System.out.println(strOtp);
		return "1111";
	}

	public static String generateRandomActivationCode() {
		String strOtp = "" + (int) (((Math.random() * (111111 - 999999)) * (-1)));
		return strOtp.replaceAll("0", "1");
	}


	public static final String zeroPadRight(String l, int size) {
		String s = l + "00000000000000000000000";
		return s.substring(0, 5);
	}

	public static String convertToLocalMobileNo(String mbNo, String countryCode) {
		String mobileNo = countryCode;
		mobileNo += mbNo.substring(1, mbNo.length());
		return mobileNo;
	}

	public static String getStr4Slit(String str) {
		String s = "\\";
		String[] ars = str.split("");

		for (int i = 1; i < ars.length; i++) {
			if (i < ars.length - 1)
				s += ars[i] + "\\";
			else
				s += ars[i];
		}

		return s;
	}

	public static Date getDateByString(String strDate, String dateFormat) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat);
		java.util.Date date;
		try {
			date = sdf1.parse(strDate);
			return new Date(date.getTime());
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(), e);
			;
			return null;
		}

	}

	public static final String getRandomString(int targetStringLength) {

		int leftLimit = 97;
		int rightLimit = 122;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();

	}
	
	
	public static boolean isNull(String str) {
		if (null == str || str.equals("") || str.equals("null"))
			return true;
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	public static String[] split(String str, char delimiter) {
		String[] singleString = {};
		if (isNull(str)) {
			return singleString;
		} else if (str.indexOf(delimiter) == -1) {
			singleString = new String[] { str };
			return singleString;
		} else {
			String strArray[] = null;
			int startIndex = 0;
			@SuppressWarnings("rawtypes")
			List arList = new ArrayList();
			int count = 0;
			int strLen = str.length();
			if (str.charAt(strLen - 1 >= 0 ? strLen - 1 : 0) == delimiter) {
				strLen++;
			}
			for (startIndex = 0; startIndex < strLen; startIndex++) {
				int endIndex = str.indexOf(delimiter, startIndex);
				if (endIndex == -1) {
					endIndex = strLen;
				}
				String val = "";
				try {
					val = str.substring(startIndex, endIndex);
				} catch (StringIndexOutOfBoundsException ste) {
					LOGGER.error(ste.getMessage(), ste);
				}
				arList.add(count, val);
				count++;
				startIndex = endIndex;
			}
			int size = arList.size();
			strArray = new String[size];
			for (int i = 0; i < size; i++) {
				strArray[i] = (String) arList.get(i);
			}
			return strArray;
		}
	}

	public static final Date getPreviousDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	public static final Date getNextDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public static String getMaskAmount(double amt1) {
		amt1 *= 100.0D;
		int amt = (int) amt1;
		char[] arAmt = ("" + amt).toCharArray();
		LOGGER.info(arAmt.length);
		for (int i = 0; i < arAmt.length; i++) {
			LOGGER.info(":" + arAmt[i]);
		}
		int[] arInt = new int[12];
		for (int i = 0; i < 12; i++) {
			arInt[i] = 0;
		}
		int len = 12 - arAmt.length;
		LOGGER.info(len);
		int j = 0;

		for (int i = len; i < 12; i++) {
			arInt[i] = Integer.parseInt("" + arAmt[j]);
			j++;
		}

		String finStr = "";
		for (int i = 0; i < 12; i++) {
			finStr = finStr + arInt[i];
		}
		return finStr;
	}

	// RSA ALGO
	public static String getRSAEncryptedString(String requestStr) {
		String stEncryptedRequest = "";
		try {
			PrivateKey publicKey = new Utils().loadRSAPublicKey();
			stEncryptedRequest = Utils.RSAencrypt(requestStr, publicKey);
			stEncryptedRequest = stEncryptedRequest.replace("\n", "");
			stEncryptedRequest = stEncryptedRequest.replace("\r", "");
			stEncryptedRequest = stEncryptedRequest + "\n";
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			;
		}
		return stEncryptedRequest;
	}

	public PrivateKey loadRSAPublicKey() {
		return getKey("QRYS.PUBLIC.KEY");

	}

	public static String RSAdecrypt(String stMessage, PrivateKey privateKey) {
		String stDecrypted = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			BASE64Decoder decode = new BASE64Decoder();
			byte[] y = cipher.doFinal(decode.decodeBuffer(stMessage));
			stDecrypted = new String(y);
		} catch (Exception e) {
			LOGGER.info("EncryptionDecryptionUtility:loadRSAPublicKey:Exception" + e);
		}
		return stDecrypted;
	}

	public static String RSAencrypt(String stMessage, PrivateKey publicKey) {
		String stEncrypted = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] x = cipher.doFinal(stMessage.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			stEncrypted = encoder.encode(x);
		} catch (Exception e) {
			LOGGER.info("EncryptionDecryptionUtility:loadRSAPublicKey:Exception" + e);
		}
		return stEncrypted;
	}

	public PrivateKey loadRSAPrivateKeyQRYS() {
		return getKey("QRYS.PRIVATE.KEY");
	}

	public PrivateKey getKey(String keyType) {
		PrivateKey pub = null;
		try {
			in = getClass().getClassLoader().getResourceAsStream(GlobalPropertyReader.getInstance().getValue(keyType));
			DataInputStream dat = new DataInputStream(in);
			int len = dat.readInt();
			byte[] enc = new byte[len];
			dat.readFully(enc);
			dat.close();
			in.close();
			in = null;
			dat = null;
			PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(enc);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			pub = factory.generatePrivate(privSpec);
		} catch (Exception e) {
			LOGGER.info("EncryptionDecryptionUtility:loadRSAPrivateKey:Exception" + e);
		}
		return pub;
	}

	/*
	 * // Encryption start public static String getEncryptedResponse(Gson gson,
	 * ResponseMessageBean obj, MiddleWareRequest request) { String
	 * jsonToOriginator; ResponseMessageBean response = new
	 * ResponseMessageBean(request); response = obj; jsonToOriginator =
	 * gson.toJson(response); return jsonToOriginator; }
	 */
	// Encryption start
	public static String getEncryptedResponse(Gson gson, ResponseMessageBean obj) {
		String jsonToOriginator;
		jsonToOriginator = gson.toJson(obj);
		return jsonToOriginator;
	}

	// Encryption end
	public static String getFormattedDate(String dateObj) {
		String timeStamp = "";
		SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			Date date = input.parse(dateObj);
			timeStamp = df.format(date);
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return timeStamp;
	}

	// Encryption end
	public static String getFormattedDate(Date dateObj) {

		DateFormat dfNew = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String reportDate = dfNew.format(dateObj);
		return reportDate;
	}

	public static Date getFormatedDateForOmni() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		ft.format(dNow);
		return dNow;
	}

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.trim().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public static Map<String, String> getRequestMap(HttpServletRequest request) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		@SuppressWarnings("rawtypes")
		Iterator iter = request.getParameterMap().keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			result.put(key, URLDecoder.decode(request.getParameter(key), "ISO-8859-1"));
		}
		return result;
	}

	/* CBI Methods START */
	public static String trim(String str) {
		if (str != null) {
			return str.trim();
		} else {
			return "";
		}
	}

	public static final String leftPad(String value, int size) {

		return String.format("%" + size + "s", value);
	}

	public static final String rightPad(String value, int size) {

		return String.format("%-" + size + "s", value);
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

	public String decrypt(String salt, String iv, String passphrase, String EncryptedText)
			throws GeneralSecurityException, IOException {
		String decryptedValue = null;
		try {
			byte[] saltBytes = Utils.hexStringToByteArray(salt);
			SecretKeySpec sKey = (SecretKeySpec) generateKeyFromPassword(passphrase, saltBytes);
			byte[] ivBytes = Utils.hexStringToByteArray(iv);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
			cipher.init(Cipher.DECRYPT_MODE, sKey, ivParameterSpec);
			byte[] decordedValue = new BASE64Decoder().decodeBuffer(EncryptedText);
			byte[] decValue = cipher.doFinal(decordedValue);
			decryptedValue = new String(decValue);
		} catch (Exception e) {
			LOGGER.error("ERROR IN: " + Utils.exceptionToString(e));
		}
		return decryptedValue;

	}

	public static SecretKey generateKeyFromPassword(String password, byte[] saltBytes) throws GeneralSecurityException {

		KeySpec keySpec = new PBEKeySpec(password.toCharArray(), saltBytes, 1000, 256);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		return new SecretKeySpec(secretKey.getEncoded(), "AES");
	}

	/* omni Encryption END */

	public static String clobStringConversion(Clob clb) throws IOException, SQLException {
		if (clb == null)
			return "";

		StringBuilder str = new StringBuilder();
		String strng;

		BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());

		while ((strng = bufferRead.readLine()) != null)
			str.append(strng);

		return str.toString();
	}

	public static String Req(String url, String json) {
		String POST_URL = GlobalPropertyReader.getInstance().getValue(url);
		StringBuilder response = new StringBuilder();
		try {
			URL obj = new URL(POST_URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

			// For POST only - START
			con.setDoOutput(true);

			OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			os.write(json);
			os.flush();
			os.close();
			int responseCode = con.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				con.disconnect();
			}
		} catch (Exception e) {
			LOGGER.info("Exception  " + e);
		}
		return response.toString();
	}

	public static String hashMac(String text, String secretKey) {
		try {
			Key sk = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
			Mac mac = Mac.getInstance(sk.getAlgorithm());
			mac.init(sk);
			final byte[] hmac = mac.doFinal(text.getBytes());
			return toHexString(hmac);
		} catch (NoSuchAlgorithmException e1) {
			LOGGER.info("Exception  " + e1);
		} catch (InvalidKeyException e) {
			LOGGER.info("Exception  " + e);
		}
		return secretKey;
	}

	public static String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);

		Formatter formatter = new Formatter(sb);
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		formatter.close();
		return sb.toString();

	}

	/*
	 * public static String encryptresponse(ResponseMessageBean resPOJO,
	 * MiddleWareRequest transactionPOJO) { Gson gson = new Gson(); String
	 * jsonToOriginator = Utils.getEncryptedResponse(gson, resPOJO);
	 * 
	 * if (null != transactionPOJO.getDeviceId() &&
	 * transactionPOJO.getDeviceId().equalsIgnoreCase("9")) { jsonToOriginator =
	 * Utils.getEncryptedResponse(gson, resPOJO, transactionPOJO); resPOJO = null;
	 * return jsonToOriginator; } else { jsonToOriginator =
	 * EncryptDeryptUtility.encryptNonAndroid(jsonToOriginator,
	 * transactionPOJO.getSecretKey()).replace("\n", "");
	 * System.out.println(jsonToOriginator.toString()); Map<String, String>
	 * mapNewTest = new HashMap<String, String>(); mapNewTest.put("data",
	 * jsonToOriginator.toString()); mapNewTest.put("subActionId",
	 * transactionPOJO.getSubActionId()); mapNewTest.put("secType",
	 * transactionPOJO.getSecretKeyType()); resPOJO = null; Gson gsonObj = new
	 * Gson(); return gsonObj.toJson(mapNewTest).toString().replace("\n", ""); }
	 * 
	 * }
	 */

	public static final String zeroPad(long l, int size) {
		String s = "00000000000000000000000" + l;
		return s.substring(s.length() - size);
	}

	public static final int zeroPad(String value, int size) {
		String s = "000000000000000000" + value;
		return Integer.parseInt(s.substring(s.length() - size));
	}

	public static String maskAccountNumber(String accountNumber) {
		String str1 = accountNumber.substring(0, 2);
		String str2 = accountNumber.substring(accountNumber.length() - 2);
		int len = accountNumber.substring(2, accountNumber.length() - 2).length();
		String s = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX".substring(0, len);
		return str1 + s + str2;
	}

	// Feild Validation
	public static List<String> feildValidation(List<String> paramsList, Map<String, String> map) {
		List<String> errorParam = new ArrayList<String>();
		for (String feild : paramsList) {
			if (isNull(map.get(feild))) {
				errorParam.add(feild);
			}
		}
		return errorParam;
	}

	public static String blobStringConversion(Blob blobData) throws IOException, SQLException {
		if (blobData == null)
			return "";

		byte[] bdata = blobData.getBytes(1, (int) blobData.length());
		String text = new String(bdata);

		return text;
	}

	public static String StringClobConverion(String custImage) {
		String custclob = "(";
		for (int i = 0; i <= custImage.length(); i = i + 4000) {
			int temp = i;
			custclob = custclob + "to_clob('";
			if ((custImage.length() - temp) >= 4000)
				if ((custImage.length() + temp) != 0)
					custclob = custclob + custImage.substring(temp, temp + 4000) + "')||";
				else
					custclob = custclob + custImage.substring(temp, temp + 4000) + "')";
			else
				custclob = custclob + custImage.substring(temp) + "'))";
		}
		return custclob;
	}

	public static BigDecimal getUpdatedBy(HttpServletRequest httpRequest) {
		try {
			return new BigDecimal(String.valueOf(httpRequest.getAttribute("updatedBy")));
		} catch (Exception e) {
			return BigDecimal.valueOf(0);
		}
	}
	
	public static String getUserId(HttpServletRequest httpRequest) {
		try {
			return String.valueOf(httpRequest.getAttribute("userName"));
		} catch (Exception e) {
			return "";
		}
	}

	public static String toCamelCase(String s) {
		String[] parts = s.split(" ");
		String camelCaseString = "";
		for (String part : parts) {
			if (part != null && part.trim().length() > 0)
				camelCaseString = camelCaseString + toProperCase(part);
			else
				camelCaseString = camelCaseString + part + " ";
		}
		return camelCaseString;
	}

	static String toProperCase(String s) {
		String temp = s.trim();
		String spaces = "";
		if (temp.length() != s.length()) {
			int startCharIndex = s.charAt(temp.indexOf(0));
			spaces = s.substring(0, startCharIndex);
		}
		temp = temp.substring(0, 1).toUpperCase() + spaces + temp.substring(1).toLowerCase() + " ";
		return temp;

	}

	public static long randomNumber() {
		long number = (long) Math.floor(Math.random() * 9_000_000_000_00L) + 1_000_000_000_00L;
		return number;
	}
	

	public static String trimAdvanced(String value) {

        Objects.requireNonNull(value);

        int strLength = value.length();
        int len = value.length();
        int st = 0;
        char[] val = value.toCharArray();

        if (strLength == 0) {
            return "";
        }

        while ((st < len) && (val[st] <= ' ') || (val[st] == '\u00A0')) {
            st++;
            if (st == strLength) {
                break;
            }
        }
        while ((st < len) && (val[len - 1] <= ' ') || (val[len - 1] == '\u00A0')) {
            len--;
            if (len == 0) {
                break;
            }
        }

        return (st > len) ? "" : ((st > 0) || (len < strLength)) ? value.substring(st, len) : value;
    }
	/*
	 * public static File generatePDFAttchment(String fileName, String Subject,
	 * List<Map<String, String>> record, String ownerpassword, String userPassWord)
	 * throws FileNotFoundException, DocumentException { Document document = new
	 * Document(); File file = new File(fileName);
	 * 
	 * // Create PDFWriter instance. PdfWriter pdfWriter; pdfWriter =
	 * PdfWriter.getInstance(document, new FileOutputStream(file)); // Add password
	 * protection. pdfWriter.setEncryption(userPassWord.getBytes(),
	 * ownerpassword.getBytes(), PdfWriter.ALLOW_PRINTING,
	 * PdfWriter.ENCRYPTION_AES_256);
	 * 
	 * document.open(); PdfPTable table = new
	 * PdfPTable(record.get(0).keySet().size());
	 * 
	 * // Add column header
	 * 
	 * for (Map.Entry<String, String> entry : record.get(0).entrySet()) { PdfPCell
	 * header = new PdfPCell(); header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	 * header.setBorderWidth(2); header.setPhrase(new Phrase(entry.getKey()));
	 * table.addCell(header); }
	 * 
	 * for (Map<String, String> data : record) { for (Map.Entry<String, String>
	 * entry : data.entrySet()) { table.addCell(entry.getValue()); } }
	 * 
	 * document.add(table); document.close(); pdfWriter.close(); return file; //
	 * Please explain logic
	 * 
	 * }
	 */

}
