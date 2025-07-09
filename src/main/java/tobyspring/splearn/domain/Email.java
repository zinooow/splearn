package tobyspring.splearn.domain;

import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record Email(
        String address
) {
    public Email {
        // 생성 시 검증
        Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-z]{2,7}$");
        if(!EMAIL_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("invalid email");
        }
    }

}
