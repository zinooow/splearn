package tobyspring.splearn.application.member;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tobyspring.splearn.application.member.provided.MemberFinder;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.application.member.required.EmailSender;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.member.*;

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
        Member member = memberFinder.find(memberId);

        member.activate();

        return memberRepository.save(member);
    }

    @Override
    public Member deactivate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.deactivate();

        return memberRepository.save(member);
    }

    @Override
    public Member updateInfo(Long memberId, MemberInfoUpdateRequest request) {
        Member member = memberFinder.find(memberId);

        checkDuplicateProfile(member, request.profileAddress());

        member.updateInfo(request);

        return memberRepository.save(member);
    }


    private void checkDuplicateProfile(Member member,String profileAddress) {
        if (profileAddress.isEmpty()) return;

        Profile currentProfile = member.getDetail().getProfile();
        if(currentProfile != null && currentProfile.address().equals(profileAddress)) return;

        if(memberRepository.findByProfile(new Profile(profileAddress)).isPresent()) {
            throw new DuplicateProfileException("이미 존재하는 프로필 주소입니다");
        }
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
