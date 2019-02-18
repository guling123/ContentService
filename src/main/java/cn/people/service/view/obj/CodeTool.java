package cn.people.service.view.obj;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Administrator
 * 
 */
public class CodeTool {
	private static IvParameterSpec IVSpec = new IvParameterSpec(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 });

	private static String Algorithm = "DESede/CBC/PKCS5Padding"; // 定义
	private static byte[] DESedeKey = "'zfe340';',fs@nf35+($%JV".getBytes();//24个字符密钥

	// 加密算法,可用
	public static byte[] getEncryptDESede(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey, IVSpec);
			return c1.doFinal(src);
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public static byte[] getDecryptDESede(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey, IVSpec);
			return c1.doFinal(src);
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 转换成十六进制字符串
	public static String getByte2Hex(byte[] b, String split) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + split;
		}
		return hs.toUpperCase();
	}

	// 生成MD5码
	public static String getMD5(byte[] src) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(src);
			return getByte2HexString(digest.digest()).toUpperCase();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// 生成SHA1码
	public static String getSHA1(byte[] src) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA1");
			digest.update(src);
			return getByte2HexString(digest.digest()).toUpperCase();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	 //生成SHA1码
	public static String getSHA1(File file) {
		if (file != null && file.exists() && file.isFile()) {
			BufferedInputStream in =null;
			try {
				byte[] buff = new byte[4096];
				int len = 0;
				in = new BufferedInputStream(new FileInputStream(file));
				MessageDigest digest = MessageDigest.getInstance("SHA1");
				while ((len = in.read(buff)) > 0) {
					digest.update(buff, 0, len);
				}
				return getByte2HexString(digest.digest()).toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}finally{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	// 将字节数组转换成十六进制字符串
	public static String getByte2HexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}

	// 将十六进制字符串转换成字节数组
	public static byte[] getHexString2byte(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		byte[] hanzi = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i += 2)
			hanzi[i / 2] = (byte) (Integer.parseInt(hexString.substring(i, i + 2), 16) & 0xff);
		return hanzi;
	}

	// 将null替换为""
	public static String getNullString(Object obj) {
		if (obj == null)
			return "";
		else
			return obj.toString();
	}

	// 加密算法,DES 密钥为8字节
	public static byte[] getEncryptDES(byte[] keybyte, byte[] src) {
		try {
			byte[] key = new byte[8];
			System.arraycopy(keybyte, 0, key, 0, keybyte.length > key.length ? key.length : keybyte.length);
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(key, "DES");
			// 加密
			Cipher c1 = Cipher.getInstance("DES");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 解密算法,DES 密钥为8字节
	public static byte[] getDecryptDES(byte[] keybyte, byte[] src) {
		try {
			byte[] key = new byte[8];
			System.arraycopy(keybyte, 0, key, 0, keybyte.length > key.length ? key.length : keybyte.length);
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(key, "DES");
			// 解密
			Cipher c1 = Cipher.getInstance("DES");
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	//将字节数组转化为int值
	public static int byte2int(byte[] arr) {
		int result = 0x00000000;
		for (int i = 0; i < arr.length; i++) {
			result = result | ((arr[i] & 0x000000ff) << ((arr.length - 1 - i) * 8));
		}
		return result;
	}

	public static byte[] int2byte(int n) {
		byte result[] = new byte[4];
		result[0] = (byte) ((n >>> 24) & 0x000000ff);
		result[1] = (byte) ((n >>> 16) & 0x000000ff);
		result[2] = (byte) ((n >>> 8) & 0x000000ff);
		result[3] = (byte) (n & 0x000000ff);
		return result;
	}

	public static byte[] int2byte(int n, int byteCount) {
		byte result[] = new byte[byteCount];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) ((n >>> ((result.length - 1 - i) * 8)) & 0x000000ff);
		}
		return result;
	}

	public static long byte2long(byte[] arr) {
		long result = 0x0000000000000000;
		for (int i = 0; i < arr.length; i++) {
			result = result | ((arr[i] & 0x00000000000000ff) << ((7 - i) * 8));
		}
		return result;
	}

	public static byte[] long2byte(int n) {
		byte result[] = new byte[8];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) ((n >>> ((7 - i) * 8)) & 0x00000000000000ff);
		}
		return result;
	}

	// 生成MD5码 length为o返回32位长,否则返回指定长度
	public static String getMD5(String src, int length) {
		String result = null;
		if (src != null) {
			try {
				MessageDigest digest = MessageDigest.getInstance("MD5");
				digest.update(src.getBytes());
				result = getByte2HexString(digest.digest()).toUpperCase();
				if (length > 0 && length < 32) {
					result = result.substring(32 - length);
				}
			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String getFromISO(String text) {
		try {
			byte[] bytes = text.getBytes("ISO-8859-1");
			return new String(bytes);
		} catch (Exception ex) {
		}
		return null;
	}

	// 将src用DESede算法加密并返回加密后的字节数组,密钥使用默认密钥
	public static String getEncryptDESedeString(String src) {
		String result = "";
		try {
			byte[] srcbytes = src.getBytes();
			byte encbytes[] = getEncryptDESede(DESedeKey, srcbytes);
			result = getByte2HexString(encbytes).toUpperCase();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String getEncryptDESedeBase64(String src) {
		String result = "";
		try {
			byte[] srcbytes = src.getBytes();
			byte encbytes[] = getEncryptDESede(DESedeKey, srcbytes);
			result = new String(Base64.encode(encbytes));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	// 将src用DESede算法加密并返回加密后的字节数组,密钥使用默认密钥
	public static String getDecryptDESedeString(String src) {
		String result = "";
		try {
			byte[] srcbytes = getHexString2byte(src);
			byte decbytes[] = getDecryptDESede(DESedeKey, srcbytes);
			result = new String(decbytes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String getDecryptDESedeBase64(String src) {
		String result = "";
		try {
			byte[] srcbytes = Base64.decode(src);
			byte decbytes[] = getDecryptDESede(DESedeKey, srcbytes);
			result = new String(decbytes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
