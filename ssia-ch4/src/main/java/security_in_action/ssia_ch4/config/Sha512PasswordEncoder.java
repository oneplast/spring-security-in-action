package security_in_action.ssia_ch4.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Sha512PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return hashWithSHA512(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedPassword = encode(rawPassword);
        return encodedPassword.equals(hashedPassword);
    }

    private String hashWithSHA512(String input) {
        StringBuilder result = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] digested = md.digest(input.getBytes());

            /*
            for (int i = 0; i < digested.length; i++) {
                result.append(Integer.toHexString(0xFF & digested[i]));
            }
            return result.toString();
             */

            // Java 17부터 사용 가능
            return HexFormat.of().formatHex(digested);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Bad algorithm");
        }
    }
}
