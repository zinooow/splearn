package tobyspring.splearn.application.member.provided;

import tobyspring.splearn.domain.member.Member;

public interface MemberFinder {
    Member find(Long memberId);
    Member find(String email);
    boolean existsMember(String email);
    boolean existsMember(Long memberId);
}
