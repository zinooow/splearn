package tobyspring.splearn.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tobyspring.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.member.MemberFixture.createPasswordEncoder;

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
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void activate() {
        member.activate();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(() ->
                member.activate()
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void deactivateFail() {
        member.activate();

        member.deactivate();

        assertThatThrownBy(() ->
                member.deactivate()
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void nullNickname() {
        assertThatThrownBy(() -> Member.register(new MemberRegisterRequest("jinho0547@naver.com", null, "secret"), null))
                .isInstanceOf(NullPointerException.class);
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
    @Test
    void updateInfo() {
        member.activate();

        var request= new MemberInfoUpdateRequest("hanjinho", "jinho0547", "안녕하세요 저는 한진호입니다");
        member.updateInfo(request);

        assertThat(member.getDetail().getProfile().address()).isEqualTo(request.profileAddress());
        assertThat(member.getNickname()).isEqualTo(request.nickname());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(request.introduction());
    }

    @Test
    void updateInfoFail() {
        var request= new MemberInfoUpdateRequest("hanjinho", "jinho0547", "안녕하세요 저는 한진호입니다");


        assertThatThrownBy(() -> member.updateInfo(request)).isInstanceOf(IllegalStateException.class);
    }

}