package tobyspring.splearn.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import tobyspring.splearn.application.provided.MemberFinder;
import tobyspring.splearn.application.required.MemberRepository;
import tobyspring.splearn.domain.Email;
import tobyspring.splearn.domain.Member;

@Service
@RequiredArgsConstructor
@Validated
public class MemberQueryService implements MemberFinder {

    private final MemberRepository memberRepository;

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found By ID: " + memberId));
    }

    @Override
    public Member findMember(String email) {
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
