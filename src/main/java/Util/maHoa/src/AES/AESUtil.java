package AES;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class AESUtil {

    public static String encrypt(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String encryptedData, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter data to encrypt: ");
            String data = scanner.nextLine();

            System.out.print("Enter 16-character key: ");
            String key = scanner.nextLine();

            if (key.length() != 16) {
                System.out.println("Key must be 16 characters long!");
                return;
            }

            String encryptedData = encrypt(data, key);
            System.out.println("Encrypted data: " + encryptedData);

            String decryptedData = decrypt(encryptedData, key);
            System.out.println("Decrypted data: " + decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
