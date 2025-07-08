package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp() {
        passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean match(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };

        member = Member.create(
                new MemberCreateRequest("jinho0547@naver.com", "jinho", "secret")
                , passwordEncoder);
    }

    @Test
    void createMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertEquals(MemberStatus.PENDING, member.getStatus());
    }

    @Test
    void nullNickname() {
        assertThatThrownBy(() -> Member.create(new MemberCreateRequest("<EMAIL>", null, "secret"), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void changeNickname() {
        member.changeNickname("Changed");

        assertThat(member.getNickname()).isEqualTo("Changed");
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();

        member.changePassword("changed", passwordEncoder);

        assertThat(member.verifyPassword("secret", passwordEncoder)).isFalse();
        assertThat(member.verifyPassword("changed", passwordEncoder)).isTrue();
    }

    @Test
    void verifyEmail() {
        assertThatThrownBy(() ->
                Member.create(new MemberCreateRequest("invalid Email", "jinho", "secret"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

    }

}