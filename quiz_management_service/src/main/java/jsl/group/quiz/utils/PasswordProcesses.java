package jsl.group.quiz.utils;

import javax.crypto.SecretKey;

public class PasswordProcesses {
    public static String passwordEncryption(String password, SecretKey secretKey) throws Exception {
        return EncryptDecrypt.encrypt(password, secretKey);
    }
    public static String passwordDecryption(String encryptedPassword, SecretKey secretKey) throws Exception {
        return EncryptDecrypt.decrypt(encryptedPassword, secretKey);
    }
}
