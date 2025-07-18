package tobyspring.splearn.application.member.required;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberInfoUpdateRequest;
import tobyspring.splearn.domain.member.MemberStatus;
import tobyspring.splearn.domain.member.Profile;
import tobyspring.splearn.domain.shared.Email;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.member.MemberFixture.createPasswordEncoder;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        assertThat(member.getId()).isNull();
        memberRepository.save(member);
        assertThat(member.getId()).isNotNull();
        entityManager.flush();
        entityManager.clear();

        var found =  memberRepository.findById(member.getId()).orElseThrow();
        assertThat(found.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(found.getDetail().getRegisteredAt()).isNotNull();

    }

    @Test
    void duplicateEmailFail() {
        Member member1 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        memberRepository.save(member1);

        Member member2 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void findByProfile() {
        Member member1 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        memberRepository.save(member1);
        member1.activate();
        memberRepository.save(member1);
        member1.updateInfo(new MemberInfoUpdateRequest("asdfasdf", "asdfasdfas","asdfasdfsfas"));
        memberRepository.save(member1);

        entityManager.flush();
        entityManager.clear();

        var foundByEmail = memberRepository.findByEmail(new Email("jinho0547@naver.com"));

        System.out.println(foundByEmail.orElseThrow().getDetail().getProfile().address());



        var found = memberRepository.findByProfile(new Profile("asdfasdfas")).orElseThrow();
        System.out.println(found.getDetail().getProfile().address());

    }


}