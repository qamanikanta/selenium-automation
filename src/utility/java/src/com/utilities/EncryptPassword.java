package src.com.utilities;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptPassword {
	private static final String ALGO = "AES";

	private static final byte[] keyValue = new byte[] { 'm', 'Y', 'p', 'R', 'i', 'V', 'A','t', 'E', 'k', 'E', 'y', 'n', 'A',
			'e', 'E', 'M' };

	
	public String decrypt(String encryptedData) {
		String decryptedValue = null;
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
			byte[] decValue = c.doFinal(decordedValue);
			decryptedValue = new String(decValue);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedValue;
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}

	public String encrypt(String password) {
		String encryptedValue = null;
		try {

			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(password.getBytes());
			encryptedValue = new BASE64Encoder().encode(encVal);

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedValue;

	}

	
	public static void main(String... args) throws Exception {
		String password = "Heidiow";
		String password1 = "H3idi2020";
		EncryptPassword enc = new EncryptPassword();
		
		  String passwordEnc = enc.encrypt(password);
		  System.out.println("Plain Text : " + password +
		  " and it's Encryption is ::    " + passwordEnc);
		 
		String passwordDec = enc.decrypt(passwordEnc);
		System.out.println("Decrypted Text : " + passwordDec);
	}
}
