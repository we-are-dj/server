package com.dj.server.api.member.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@RequiredArgsConstructor
public class MemberSaveRequestDTO {

    private final String code;
    private final String redirectUri;

}
