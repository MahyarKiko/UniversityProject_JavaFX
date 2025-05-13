package application;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.io.BufferedWriter;


/**
 * die Utility Klasse bietet Hilfsfunktionen fuer das Schreiben und Lesen von Daten von Json Dateien
 */

public class Utility {
	
	private static final String AES_SECRET_KEY = "oAHGsW2Ov0m0eKtJ1yzISQ==";
	
	
	/**
     * schreibt eine liste von Objekten in eine Json Datei
     *
     * @param dataList Die Liste von Objekten, die in die Json Datei geschrieben werden sollen
     * @param filePath Der Pfad zur Json Datei
     */
	
	public void writeToJsonFile(List<Item> todos, String filePath) {
		try {
			Gson gson = new Gson();
			Type todosType = new TypeToken<List<Item>>() {
			}.getType();
			String json = gson.toJson(todos, todosType);
			String encriptingJson = encrypt(json); 

			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
			bufferedWriter.write(encriptingJson);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
     * liest Daten aus einer Json Datei und gibt eine Liste von Objekten zurueck
     *
     * @param filePath Der Pfad zur Json Datei
     * @return Eine Liste von Objekten,die aus der Json Datei gelesen wurden
     */
	

	public List<Item> readFromJsonFile(String filePath) {
		try {

			File file = new File(filePath);
			List<Item> personList = new ArrayList<>();

			if (file.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
				String decrypyJson = decrypt(bufferedReader.readLine());
				
				Gson gson = new Gson();
				Type todosType = new TypeToken<List<Item>>() {
				}.getType();
				personList = gson.fromJson(decrypyJson, todosType);
				bufferedReader.close();
			}
			if (personList == null) {
				personList = new ArrayList<>();
			}
			return personList;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	 /**
     * verschluesselt eine Eingabezeichenkette mithilfe des AES Algorithmus
     *
     * @param input Die Eingabezeichenkette, die verschluesselt werden soll
     * @return Die verschluesselte Zeichenkette im Base64 Format
     */
	
	 private static String encrypt(String input) {
	        try {
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            SecretKeySpec secretKeySpec = new SecretKeySpec(getAESSecretKey().getEncoded(), "AES");
	            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
	            byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
	            return Base64.getEncoder().encodeToString(encryptedBytes);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 /**
	     * entschluesselt eine verschluesselte Zeichenkette mithilfe des AES Algorithmus
	     *
	     * @param encryptedInput Die verschluesselte Eingabezeichenkette im Base64 Format
	     * @return Die entschluesselte Zeichenkette
	     */
	 
	 private static String decrypt(String encryptedInput) {
	        try {
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            SecretKeySpec secretKeySpec = new SecretKeySpec(getAESSecretKey().getEncoded(), "AES");
	            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
	            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedInput));
	            return new String(decryptedBytes, StandardCharsets.UTF_8);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 /**
	     * generiert einen geheimen AES Schluessel aus einem vorgegebenen Schluessel
	     *
	     * @return Der generierte geheime AES Schluessel
	     * @throws Exception Wenn ein Fehler beim Generieren des Schluessels auftritt
	     */
	 
	 private static SecretKeySpec getAESSecretKey() throws Exception {
		 
		 // /Der AES Schluessel muss mindestens 16 Bytes lang sein. 
		 //Es wird SHA-1 zur Generierung verwendet
	        byte[] key = (AES_SECRET_KEY).getBytes(StandardCharsets.UTF_8);
	        MessageDigest sha = MessageDigest.getInstance("SHA-1");
	        key = sha.digest(key);
	        key = java.util.Arrays.copyOf(key, 16); // Schluessel auf 16 Bytes Laenge kuerzen
	        return new SecretKeySpec(key, "AES");
	    }

}
