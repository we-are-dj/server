package com.dj.server.api.musiclist.repository;


import com.dj.server.api.musiclist.dto.response.MusicAllListResponseDTO;
import com.dj.server.api.musiclist.entity.MusicList;
import com.dj.server.api.musiclist.entity.QMusicList;
import com.dj.server.api.playlist.entity.PlayList;
import com.dj.server.api.playlist.entity.QPlayList;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class MusicListQueryDSLRepositoryImpl extends QuerydslRepositorySupport implements MusicListQueryDSLRepository {

    private final QMusicList musicList = QMusicList.musicList;
    private final QPlayList playList = QPlayList.playList;

    public MusicListQueryDSLRepositoryImpl() {
        super(MusicList.class);
    }

    @Override
    public List<MusicAllListResponseDTO> findByMusicList(Long playListId) {

        return from(musicList)
                .innerJoin(playList).on(musicList.playList.eq(playList))
                .where(playList.playListId.eq(playListId))
                .select(Projections.constructor(MusicAllListResponseDTO.class,
                        musicList.musicId, musicList.musicNo, musicList.musicUrl ))
                .fetch();
    }


    /**
     *
     * 해당 재생목록의 마지막 번호를 리턴합니다
     * select music_no from music m inner join playlist p on m.play_list_id = p.play_list_id
     * where p.play_list_id = ?
     * order by b.music_no desc
     *
     * @param playListId
     * @return Integer
     */
    @Override
    public Integer findByPlayListLastMusicNo(Long playListId) {
        try {  // NPE 떠서 추가.
            return from(musicList)
                    .innerJoin(playList).on(musicList.playList.eq(playList))
                    .where(playList.playListId.eq(playListId))
                    .orderBy(musicList.musicNo.desc())
                    .fetchFirst().getMusicNo();
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
