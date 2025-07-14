package tobyspring.splearn.domain;

import tobyspring.splearn.domain.member.MemberRegisterRequest;
import tobyspring.splearn.domain.member.PasswordEncoder;

public class MemberFixture {

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "jinho", "valid_secret");
    }
    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("jinho0547@naver.com");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }
}
