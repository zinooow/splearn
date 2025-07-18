package tobyspring.splearn.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
class MemberFinderTest {
    private final MemberFinder memberFinder;
    private final MemberRegister memberRegister;
    private final EntityManager entityManager;

    public MemberFinderTest(MemberFinder memberFinder, MemberRegister memberRegister, EntityManager entityManager) {
        this.memberFinder = memberFinder;
        this.memberRegister = memberRegister;
        this.entityManager = entityManager;
    }

    @Test
    void find() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member foundMember = memberFinder.find(member.getId());
        Member foundMember2 = memberFinder.find(member.getEmail().address());

        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getId()).isEqualTo(member.getId());
        assertThat(foundMember2.getId()).isEqualTo(member.getId());
    }

    @Test
    void findFailMember() {
        assertThatThrownBy(() -> memberFinder.find(999L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void existsMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        assertThat(memberFinder.existsMember(member.getId())).isTrue();
        assertThat(memberFinder.existsMember(999L)).isFalse();
        assertThat(memberFinder.existsMember(member.getEmail().address())).isTrue();
        assertThat(memberFinder.existsMember("google@google.com")).isFalse();
    }

}