package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberRegisterRequest;
import tobyspring.splearn.domain.member.MemberStatus;
import tobyspring.splearn.domain.member.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.MemberFixture.createPasswordEncoder;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = createPasswordEncoder();
        member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertEquals(MemberStatus.PENDING, member.getStatus());
    }

    @Test
    void nullNickname() {
        assertThatThrownBy(() -> Member.register(new MemberRegisterRequest("jinho0547@naver.com", null, "secret"), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void changeNickname() {
        member.changeNickname("Changed");

        assertThat(member.getNickname()).isEqualTo("Changed");
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("valid_secret", passwordEncoder)).isTrue();

        member.changePassword("valid_changed", passwordEncoder);

        assertThat(member.verifyPassword("valid_secret", passwordEncoder)).isFalse();
        assertThat(member.verifyPassword("valid_changed", passwordEncoder)).isTrue();
    }

    @Test
    void verifyEmail() {
        assertThatThrownBy(() ->
                Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

    }

}