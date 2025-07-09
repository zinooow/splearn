package tobyspring.splearn.domain;

public interface PasswordEncoder {
    String encode(String password);
    boolean match(String password, String passwordHash);
}
