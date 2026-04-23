package kr.goodmobilcar;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenTest {
    @Test
    void gen() {
        System.out.println(new BCryptPasswordEncoder().encode("demo1234!"));
    }
}
