package tobyspring.splearn.domain;

import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public class Member {
    private Email email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    private Member() {}

    public static Member create(MemberCreateRequest createRequest, PasswordEncoder passwordEncoder) {

        Member member = new Member();

        member.email = new Email(requireNonNull(createRequest.email()));
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = passwordEncoder.encode(requireNonNull(createRequest.password()));

        member.status = MemberStatus.PENDING;

        return member;
    }


    public void activate() {
        this.status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = MemberStatus.DEACTIVATED;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(password);
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.match(password, passwordHash);
    }


}
