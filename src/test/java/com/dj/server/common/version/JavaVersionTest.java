package com.dj.server.common.version;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class JavaVersionTest {

    @Test
    @DisplayName("자바 버전을 테스트합니다.")
    public void javaVersionTests() {

        String s = "*";

        //자바11의 함수를 사용합니다
        String java11 = s.repeat(5);

        //검증
        assertThat(java11).isEqualTo("*****");

    }

}
