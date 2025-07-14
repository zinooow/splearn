package tobyspring.splearn.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import tobyspring.splearn.domain.shared.AbstractEntity;
import tobyspring.splearn.domain.shared.Email;

import static java.util.Objects.requireNonNull;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractEntity {

    @NaturalId
    private Email email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    @OneToOne
    private MemberDetail detail;

    public static Member register(MemberRegisterRequest createRequest, PasswordEncoder passwordEncoder) {

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
        return passwordEncoder.matches(password, passwordHash);
    }

}
