package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    // Hash mật khẩu
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Kiểm tra mật khẩu
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
