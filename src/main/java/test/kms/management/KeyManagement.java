package test.kms.management;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

import test.kms.util.PathUtils;

public class KeyManagement {

	public static final String PUB_KEY = "pub.key";
	public static final String PRI_KEY = "pri.key";
	
	public static final String STORE_PATH;
	
	static {
		String homePath = PathUtils.findHomePath();
		STORE_PATH = homePath + File.separatorChar + "store";
	}
	
	public static String getStringPublicKey(String name, int length, boolean genNewKey, boolean autoGenFirstKey) {
		if (genNewKey) {
			try {
				saveNewKeyPair(name, length);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return getStringPublicKey(name, length, false, false);
		} else if (existKey(name, length)) {
			File pubKey = new File(STORE_PATH + File.separatorChar + name + length + File.separatorChar + PUB_KEY);
			
			try {
				String stringPubKey = FileUtils.readFileToString(pubKey);
				return stringPubKey;
			} catch (IOException e) {
				return "notfindkey";
			}
		} else if (autoGenFirstKey) {
			return getStringPublicKey(name, length, true, false);
		} else {
			return "notfindkey";
		}
	}
	
	public static String getStringPriavteKey(String name, int length, boolean genNewKey, boolean autoGenFirstKey) {
		if (genNewKey) {
			try {
				saveNewKeyPair(name, length);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return getStringPriavteKey(name, length, false, false);
		} else if (existKey(name, length)) {
			File priKey = new File(STORE_PATH + File.separatorChar + name
					+ length + File.separatorChar + PRI_KEY);

			try {
				String stringPriKey = FileUtils.readFileToString(priKey);

				return stringPriKey;
			} catch (IOException e) {
				return "notfindkey";
			}
		} else if (autoGenFirstKey) {
			return getStringPriavteKey(name, length, true, false);
		} else {
			return "notfindkey";
		}
	}
	
	public static boolean existKey(String name, int length) {
		File store = new File(STORE_PATH);
		String[] filesName = store.list();
		boolean findedKey = false;
		if (filesName != null) {
			for (String fileName : filesName) {
				if (fileName.equals(name + length)) {
					findedKey = true;
					break;
				}
			}
		}
		return findedKey;
	}
	
	public static void saveNewKeyPair(String name, int length) throws Exception  {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(length);
		KeyPair key = keyGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) key.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) key.getPrivate();
		
		 String pubKeyPath = STORE_PATH + File.separatorChar + name + length + File.separatorChar + PUB_KEY;
		 String priKeyPath = STORE_PATH + File.separatorChar + name + length + File.separatorChar + PRI_KEY;

		 FileUtils.writeStringToFile(new File(pubKeyPath), new String(Base64.getEncoder().encode(publicKey.getEncoded())));
		 FileUtils.writeStringToFile(new File(priKeyPath), new String(Base64.getEncoder().encode(privateKey.getEncoded())));
		 
	}
	
}
