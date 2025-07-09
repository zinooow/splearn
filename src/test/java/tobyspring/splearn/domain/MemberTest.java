package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.MemberFixture.getPasswordEncoder;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = getPasswordEncoder();
        member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertEquals(MemberStatus.PENDING, member.getStatus());
    }

    @Test
    void nullNickname() {
        assertThatThrownBy(() -> Member.register(new MemberRegisterRequest("<EMAIL>", null, "secret"), null))
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
                Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

    }

}