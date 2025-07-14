package tobyspring.splearn.application.member.provided;

import tobyspring.splearn.domain.member.Member;

public interface MemberFinder {
    Member findMember(Long memberId);
    Member findMember(String email);
    boolean existsMember(String email);
    boolean existsMember(Long memberId);
}
