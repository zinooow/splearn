package tobyspring.splearn.domain.member;

import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

public record MemberInfoUpdateRequest(
        @Size(min = 5, max = 20) String nickname,
        @Size(max=15) String profileAddress,
        @NonNull String introduction
        ) {
}
