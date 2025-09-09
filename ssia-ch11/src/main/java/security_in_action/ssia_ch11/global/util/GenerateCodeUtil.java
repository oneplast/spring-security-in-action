package security_in_action.ssia_ch11.global.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GenerateCodeUtil {

    public static String generateCode() {
        String code;

        try {
            SecureRandom random = SecureRandom.getInstanceStrong(); // 임의의 int 값 생성하는 SecureRandom 인스턴스

            int c = random.nextInt(9000) + 1000;    // 1000 ~ 9999 사이의 값

            code = String.valueOf(c);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem when generating the random code.");
        }

        return code;
    }
}
