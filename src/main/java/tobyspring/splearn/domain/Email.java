package tobyspring.splearn.domain;

import java.util.regex.Pattern;

public record Email(
        String address
) {
    private static Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9_+&*-]+(?:\\.[A-Za-z0-9_+&*-]+)*@(?:[A-Za-z0-9-]+\\.)+[a-z]{2,7}$");

    public Email {
        if(!EMAIL_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("invalid email address");
        }
    }

}
