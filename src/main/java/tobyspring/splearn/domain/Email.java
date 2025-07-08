package tobyspring.splearn.domain;

import java.util.regex.Pattern;

public record Email(
        String email
) {
    public Email {
        // 생성 시 검증
        Pattern EMAIL_PATTERN =
                Pattern.compile("^[A-Za-z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-z]{2,7}$");
        if(!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("invalid email");
        }
    }

}
