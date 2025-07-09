package tobyspring.splearn.domain;

public class MemberFixture {

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "jinho", "secret");
    }
    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("jinho0547@naver.com");
    }

    public static PasswordEncoder getPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean match(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }
}
