package tobyspring.splearn.adapter.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import tobyspring.splearn.adapter.webapi.dto.MemberRegisterResponse;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.domain.member.MemberRegisterRequest;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(MemberApi.class)
@RequiredArgsConstructor
class MemberApiWebMvcTest {

    @MockitoBean
    MemberRegister memberRegister;

    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;

    @Test
    void register() throws JsonProcessingException, UnsupportedEncodingException {
        Long registeredId = 1L;
        when(memberRegister.register(any())).thenReturn(MemberFixture.createMember(registeredId));
        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
        String requestJson = objectMapper.writeValueAsString(request);


        MvcTestResult result = mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.memberId").asNumber().isEqualTo(1);

        MemberRegisterResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), MemberRegisterResponse.class);

        verify(memberRegister).register(any());

    }

    @Test
    void registerFail() throws JsonProcessingException {
        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest("invalid email");
        String requestJson = objectMapper.writeValueAsString(request);

        assertThat(mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .hasStatus(HttpStatus.BAD_REQUEST);



    }

}