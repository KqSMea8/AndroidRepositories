package com.lenovo.androidrepositories.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Base64;

/**
 * 编码工具�?
 * 
 * @author lenovo
 * 
 */
public class EncodedUtil {

	/**
	 * MD5加密
	 * 
	 * @param str
	 *            要加密的字符
	 * @return 加密后的字符
	 */
	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = str.getBytes("UTF-8");
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			str = hexValue.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return str;
	}

	/**
	 * 选出字符串中的数字
	 * 
	 * @param str
	 * @return
	 */
	public static String getInt(String str) {
		Pattern p = Pattern.compile("[^0-9]");
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

	/**
	 * String 转 Base64编码
	 * 
	 * @param str
	 */

	public static String StringToBase64(String str) {
		return Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
	}

	/**
	 * base64 转 String
	 * 
	 * @param str
	 * @return
	 */
	public static String base64ToString(String str) {
		byte[] bytes = Base64.decode(str, Base64.NO_WRAP);
		try {
			return new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Byte转Bit
	 */
	public static String byteToBit(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1)
				+ (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1) + (byte) ((b >> 1) & 0x1)
				+ (byte) ((b >> 0) & 0x1);
	}

	/**
	 * Bit转Byte
	 */
	public static byte BitToByte(String byteStr) {
		int re, len;
		if (null == byteStr) {
			return 0;
		}
		len = byteStr.length();
		if (len != 4 && len != 8) {
			return 0;
		}
		if (len == 8) {// 8 bit处理
			if (byteStr.charAt(0) == '0') {// 正数
				re = Integer.parseInt(byteStr, 2);
			} else {// 负数
				re = Integer.parseInt(byteStr, 2) - 256;
			}
		} else {// 4 bit处理
			re = Integer.parseInt(byteStr, 2);
		}
		return (byte) re;
	}


}
