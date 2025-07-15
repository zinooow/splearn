package tobyspring.splearn.domain;

import org.junit.jupiter.api.Test;
import tobyspring.splearn.domain.shared.Email;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @Test
    void equality() {
        var email1 = new Email("jinho0547@naver.com");
        var email2 = new Email("jinho0547@naver.com");

        assertThat(email1).isEqualTo(email2);
    }
}
