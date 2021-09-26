package com.dj.server.api.member.controller;

import com.dj.server.api.member.model.dto.request.MemberSaveRequestDTO;
import com.dj.server.api.member.model.dto.response.ResponseTokenDTO;
import com.dj.server.api.member.model.vo.kakao.KakaoProfile;
import com.dj.server.api.member.model.vo.kakao.KakaoToken;
import com.dj.server.api.member.service.MemberService;
import com.dj.server.api.member.service.oauth2.kakao.request.KakaoRequest;
import com.dj.server.common.exception.member.handler.InvalidMemberParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
//@MockitoSettings(strictness = Strictness.LENIENT) // for disable junit complain when unnessary stubbing
public class MemberControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberService memberService;

    @Mock
    private KakaoRequest kakaoRequest;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .setControllerAdvice(new MemberControllerAdvice())
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("로그인 요청시 헤더에 인가코드(code)가 없는 경우")
    @ExceptionHandler(InvalidMemberParameterException.class)
    public void headerDoesNotHaveKakaoAuthCode() throws Exception {
        this.mockMvc
                .perform(post("/v1/login/oauth2/kakao")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    @DisplayName("로그인 요청시 헤더에 code 값이 비어있는 경우")
    @ExceptionHandler(InvalidMemberParameterException.class)
    public void headerHaveEmptyKakaoAuthCode() throws Exception {
        this.mockMvc
                .perform(post("/v1/login/oauth2/kakao")
                        .header("code", "")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    @DisplayName("로그인 요청시 헤더에 redirectUri 값이 없는 경우")
    @ExceptionHandler(InvalidMemberParameterException.class)
    public void headerDoesNotHaveRedirectUri() throws Exception {
        this.mockMvc
                .perform(post("/v1/login/oauth2/kakao")
                        .header("code", "카카오인가코드")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    @DisplayName("로그인 요청시 헤더에 redirectUri 값이 비어있는 경우")
    @ExceptionHandler(InvalidMemberParameterException.class)
    public void headerHaveEmptyRedirectUri() throws Exception {
        this.mockMvc
                .perform(post("/v1/login/oauth2/kakao")
                        .header("code", "카카오인가코드")
                        .header("redirectUri", "")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    @DisplayName("지원되지 않는 http method로 요청이 왔을 경우")
    public void httpRequestMethodNotSupported() throws Exception {
        this.mockMvc.perform(get("/v1/login/oauth2/kakao"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @Order(6)
    @DisplayName("로그인 요청에 틀린 인가코드와 올바른 리다이렉트 uri가 전달되어와진 경우")
    public void expect_Get_Null_From_KAKAO_byCauseOf_Wrong_AuthCode() {
        // given
        MemberSaveRequestDTO saveDto = new MemberSaveRequestDTO("wrong_auth_code", "valid_redirect_uri");
        given(kakaoRequest.getKakaoAccessToken(saveDto.getCode(), saveDto.getRedirectUri())).willReturn(null);

        //when, then
        assertThat(kakaoRequest.getKakaoAccessToken(saveDto.getCode(), saveDto.getRedirectUri())).isEqualTo(null);
    }

    @Test
    @Order(7)
    @DisplayName("로그인 요청에 올바른 인가코드와 틀린 리다이렉트 uri가 전달되어와진 경우")
    public void expect_Get_Null_From_KAKAO_byCauseOf_Wrong_RedirectUri() {
        // given
        MemberSaveRequestDTO saveDto = new MemberSaveRequestDTO("valid_auth_code", "wrong_redirect_uri");
        given(kakaoRequest.getKakaoAccessToken(saveDto.getCode(), saveDto.getRedirectUri())).willReturn(null);

        //when, then
        assertThat(kakaoRequest.getKakaoAccessToken(saveDto.getCode(), saveDto.getRedirectUri())).isEqualTo(null);
    }

    @Test
    @Order(8)
    @DisplayName("로그인 요청에 올바른 카카오 인가코드도 있고 올바른 리다이렉트 uri도 있는 경우")
    public void 카카오_정보를_사용하여_토큰_생성하기() {
        // given
        MemberSaveRequestDTO saveDto = new MemberSaveRequestDTO("valid_auth_code", "valid_redirect_uri");
        ResponseTokenDTO resDto = new ResponseTokenDTO("gen_access_token", "gen_refresh_token");
        KakaoToken kakaoToken = new KakaoToken();
        KakaoProfile profile = new KakaoProfile();
        given(kakaoRequest.getKakaoAccessToken("valid_auth_code", "valid_redirect_uri")).willReturn(kakaoToken);
        given(memberService.getGeneratedTokens(profile)).willReturn(resDto);

        //when, then
        assertThat(kakaoRequest.getKakaoAccessToken(saveDto.getCode(), saveDto.getRedirectUri())).isEqualTo(kakaoToken);
        assertThat(memberService.getGeneratedTokens(profile)).isEqualTo(resDto);

    }
}
