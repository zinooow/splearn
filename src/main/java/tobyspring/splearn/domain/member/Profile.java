package tobyspring.splearn.domain.member;

import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record Profile(String address) {
    private static Pattern PROFILE_ADDRESS_PATTERN =
            Pattern.compile("[a-z0-9]+");
    public Profile {
        // 생성 시 검증
        if(address == null || (!address.isEmpty() && !PROFILE_ADDRESS_PATTERN.matcher(address).matches())) {
            throw new IllegalArgumentException("프로필 주소 형식이 올바르지 않습니다. " + address);
        }
        if(address.length() > 15) throw new IllegalArgumentException("프로필 주소는 최대 15자리를 넘을 수 없습니다.");
    }

    public String url() {
        return "@"+address;
    }
}
