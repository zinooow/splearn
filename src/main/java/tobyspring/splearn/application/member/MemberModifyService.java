package tobyspring.splearn.application.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tobyspring.splearn.application.member.provided.MemberFinder;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.application.member.required.EmailSender;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.member.DuplicateEmailException;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberRegisterRequest;
import tobyspring.splearn.domain.member.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Validated
public class MemberModifyService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final MemberFinder memberFinder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        checkDuplicateEmail(registerRequest.email());

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.findMember(memberId);

        member.activate();

        return memberRepository.save(member);
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateEmail(String email) {
        if(memberFinder.existsMember(email)) {
            throw new DuplicateEmailException("Email이 중복되었습니다." + email);
        }
    }
}
