package tobyspring.splearn.adapter.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurePasswordEncoderTest {

    @Test
    void securePasswordEncoder() {
        var securePasswordEncoder = new SecurePasswordEncoder();
        String passwordHash = securePasswordEncoder.encode("secret");

        assertTrue(securePasswordEncoder.matches("secret", passwordHash));
    }

}