package tobyspring.splearn.adapter.webapi.dto;

import tobyspring.splearn.domain.member.Member;

public record MemberRegisterResponse(
        Long memberId,
        String nickname,
        String email
) {

    public static MemberRegisterResponse of(Member member) {
        return new MemberRegisterResponse(
                member.getId(),
                member.getNickname(),
                member.getEmail().address()
        );
    }
}
