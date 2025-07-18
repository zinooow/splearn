package tobyspring.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProfileTest {

    @Test
    void profile() {
        new Profile("jinho");
        new Profile("1241244");
        new Profile("jinho0547");
        new Profile("");
    }

    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile("jinhojinhojinhojinho")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        var profile = new Profile("jinho");

        assertThat(profile.url()).isEqualTo("@jinho");
    }

}