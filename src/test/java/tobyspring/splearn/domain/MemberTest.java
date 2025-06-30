package tobyspring.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void createMember() {
        var member = new Member("jinho0547@naver.com", "jinho", "secret");
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertEquals(MemberStatus.PENDING, member.getStatus());
    }

    @Test
    void nullNickname() {
        assertThatThrownBy(() -> new Member("<EMAIL>", null, "secret"))
                .isInstanceOf(NullPointerException.class);
    }

}