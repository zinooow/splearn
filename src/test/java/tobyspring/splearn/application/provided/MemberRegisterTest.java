package tobyspring.splearn.application.provided;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.domain.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
class MemberRegisterTest {
    private final MemberRegister memberRegister;
    private final EntityManager entityManager;

    public MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {
        this.memberRegister = memberRegister;
        this.entityManager = entityManager;
    }


    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void registerDuplicateEmail() {
        Member member1 = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        assertThat(member1.getId()).isNotNull();

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);

    }

    @Test
    void activate() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void memberRegisterValidationFail() {
        valid(new MemberRegisterRequest("invalid email", "nickname", "valid_secret"));
        valid(new MemberRegisterRequest("jinho0547@naver.com", " ", "valid_secret"));
        valid(new MemberRegisterRequest("jinho0547@naver.com", "nickname", " "));
    }

    private void valid(MemberRegisterRequest request) {
        assertThatThrownBy(() -> memberRegister.register(request))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
