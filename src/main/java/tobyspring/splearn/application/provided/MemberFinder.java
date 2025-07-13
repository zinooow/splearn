package tobyspring.splearn.application.provided;

import tobyspring.splearn.domain.Member;

public interface MemberFinder {
    Member findMember(Long memberId);
    Member findMember(String email);
    boolean existsMember(String email);
    boolean existsMember(Long memberId);
}
