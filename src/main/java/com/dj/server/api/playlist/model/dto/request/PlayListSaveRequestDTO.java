package com.dj.server.api.playlist.model.dto.request;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.playlist.entity.PlayList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 재생목록을 저장할때 파라미터로 들어오는 값들을 저장할 DTO 입니다.
 *
 * @author JaeHyun
 * @create 2021-08-15
 * @since 0.0.1
 */

@Getter
@RequiredArgsConstructor
public class PlayListSaveRequestDTO {

    @ApiModelProperty(required = true, value = "재생목록 이름")
    @NotNull
    private final String playListName;

    public PlayList toEntity(Member member) {
        return PlayList.builder()
                .member(member)
                .playListName(playListName)
                .use("N")
                .build();
    }

}
