package lab6.tools;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserData implements Serializable {
    private String login;
    private String hashPassword;

    public UserData(String login, String password) {
        this.login = login;
        hashPassword = sha1(password);
    }

    public static String sha1(String text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException ignored) {}
        byte[] bytes = messageDigest.digest(text.getBytes());
        return HexBin.encode(bytes);
    }

    public String getLogin() {
        return login;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "login='" + login + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                '}';
    }
}
