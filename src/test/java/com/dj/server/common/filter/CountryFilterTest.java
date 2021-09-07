package com.dj.server.common.filter;

import com.dj.server.common.exception.common.BizException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.*;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 국가제한 필터 테스트
 *
 * @author informix
 * @see CountryFilter
 * @since 0.0.1
 */
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class CountryFilterTest {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private SessionLocaleResolver resolver;
    private MockHttpSession session;

    @InjectMocks
    private CountryFilter countryFilter;
    private MockFilterChain filterChain;

    @BeforeEach
    public void setUp() throws ServletException {
        request = new MockHttpServletRequest();
        resolver = new SessionLocaleResolver();
        response = new MockHttpServletResponse();
        session = (MockHttpSession) request.getSession();
        filterChain = new MockFilterChain();
    }

    @Test
    @DisplayName("로케일 변경 테스트")
    public void testResolveLocale() {
        Objects.requireNonNull(request.getSession()).setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
        assertEquals(Locale.KOREAN, resolver.resolveLocale(request));
    }

    @Test
    @DisplayName("로케일 및 세션 변경 테스트")
    public void testSetAndResolveLocale() {
        resolver.setLocale(request, response, Locale.KOREAN);
        assertEquals(Locale.KOREAN, resolver.resolveLocale(request));
        request.setSession(session);
        assertEquals(Locale.KOREAN, resolver.resolveLocale(request));
    }

    @Test
    @DisplayName("기본 로케일 변경 테스트")
    public void testsetDefaultLocale() {
        Locale locale = new Locale("ko", "KR");
        Locale.setDefault(locale);
        assertEquals(locale.getCountry(), "KR");

        locale = new Locale("ja", "jp");
        Locale.setDefault(locale);
        assertEquals(locale.getCountry(), "JP");
    }

    @Test
    @DisplayName("세션을 이용하지 않고 로케일 변경 테스트")
    public void testResolveLocaleWithoutSession() {
        request.addPreferredLocale(Locale.TAIWAN);
        assertEquals(request.getLocale(), resolver.resolveLocale(request));
    }

    @Test
    @DisplayName("기본 로케일 및 세션을 이용하지 않고 로케일 변경 테스트")
    public void testResolveLocaleWithoutSessionAndDefaultLocale() {
        request.addPreferredLocale(Locale.TAIWAN);
        resolver.setDefaultLocale(Locale.KOREAN);

        assertEquals(Locale.KOREAN, resolver.resolveLocale(request));
    }

    @Test
    @DisplayName("로케일이 설정되지 않은 상태에서 로케일 변경 테스트")
    public void testSetLocaleToNullLocale() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addPreferredLocale(Locale.TAIWAN);
        Objects.requireNonNull(request.getSession()).setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
        resolver.setLocale(request, response, null); // set locale null
        Locale locale = (Locale) request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        assertNull(locale);

        HttpSession session = request.getSession();

        request.addPreferredLocale(Locale.TAIWAN);
        request.setSession(session);

        assertEquals(Locale.TAIWAN, resolver.resolveLocale(request));
    }

    @Test
    @DisplayName("한국에서 요청이 올 경우")
    public void user_from_ko_KR() throws Exception {
        request.addPreferredLocale(Locale.KOREA);
        countryFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("미국에서 요청이 올 경우")
    public void user_from_en_US() throws Exception {
        request.addPreferredLocale(Locale.US);
        countryFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("중국에서 요청이 올 경우")
    public void user_from_zh_CN() {
        request.addPreferredLocale(Locale.CHINA);
        assertThrows(BizException.class, () -> countryFilter.doFilter(request, response, filterChain));
    }

    @Test
    @DisplayName("일본에서 요청이 올 경우")
    public void user_from_ja_JP() {
        request.addPreferredLocale(Locale.JAPAN);
        assertThrows(BizException.class, () -> countryFilter.doFilter(request, response, filterChain));
    }
}