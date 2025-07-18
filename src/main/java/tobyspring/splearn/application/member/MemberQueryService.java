package tobyspring.splearn.application.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tobyspring.splearn.application.member.provided.MemberFinder;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.shared.Email;
import tobyspring.splearn.domain.member.Member;

@Service
@RequiredArgsConstructor
@Validated
public class MemberQueryService implements MemberFinder {

    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found By ID: " + memberId));
    }

    @Override
    public Member find(String email) {
        return memberRepository.findByEmail(new Email(email)).orElseThrow(() -> new IllegalArgumentException("Member not found By Email: " + email));
    }

    @Override
    public boolean existsMember(String email) {
        return memberRepository.existsByEmail(new Email(email));
    }

    @Override
    public boolean existsMember(Long memberId) {
        return memberRepository.existsById(memberId);
    }
}
