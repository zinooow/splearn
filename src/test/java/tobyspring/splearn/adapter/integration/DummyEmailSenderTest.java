package tobyspring.splearn.adapter.integration;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import tobyspring.splearn.domain.Email;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DummyEmailSenderTest {
    @Test
    @StdIo
    void dummyEmailSender(StdOut stdOut) {
        var dummyEmailSender = new DummyEmailSender();
        dummyEmailSender.send(new Email("jinho0547@naver.com"), "subject", "body");
        assertThat(stdOut.capturedLines()[0]).isEqualTo("DUMMY EMAIL SENT: Email[address=jinho0547@naver.com]");
    }

}