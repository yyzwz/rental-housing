package core.util;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class DESede {

	public static final String DESede = "DESede";

	public static String decode(String secretKey, String cipherText) throws GeneralSecurityException {
		byte[] kb = secretKey.getBytes();
		SecretKeySpec k = new SecretKeySpec(kb, DESede);
		Cipher cp = Cipher.getInstance(DESede);
		cp.init(Cipher.DECRYPT_MODE, k);
		byte[] srcByte = cp.doFinal(cipherText.getBytes());
		return new String(srcByte);
	}

	public static String decode(String secretKey, byte[] cipherByte) throws GeneralSecurityException {
		byte[] kb = secretKey.getBytes();
		SecretKeySpec k = new SecretKeySpec(kb, DESede);
		Cipher cipher = Cipher.getInstance(DESede);
		cipher.init(Cipher.DECRYPT_MODE, k);
		byte[] srcByte = cipher.doFinal(cipherByte);
		return new String(srcByte);
	}

	public static String encode(String secretKey, String srcText) throws GeneralSecurityException {
		byte[] kb = secretKey.getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(kb, DESede);
		Cipher cipher = Cipher.getInstance(DESede);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		byte[] cipherByte = cipher.doFinal(srcText.getBytes());
		return new String(cipherByte);
	}

	public static String encode(String secretKey, byte[] srcByte) throws GeneralSecurityException {
		byte[] kb = secretKey.getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(kb, DESede);
		Cipher cipher = Cipher.getInstance(DESede);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		byte[] cipherByte = cipher.doFinal(srcByte);
		return new String(cipherByte);
	}

}
