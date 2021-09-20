package com.dj.server.api.musiclist.repository;


import com.dj.server.api.musiclist.model.dto.response.MusicAllListResponseDTO;
import com.dj.server.api.musiclist.entity.MusicList;
import com.dj.server.api.musiclist.entity.QMusicList;
import com.dj.server.api.playlist.entity.QPlayList;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MusicListQueryDSLRepositoryImpl extends QuerydslRepositorySupport implements MusicListQueryDSLRepository {

    private final QMusicList musicList = QMusicList.musicList;
    private final QPlayList playList = QPlayList.playList;

    public MusicListQueryDSLRepositoryImpl() {
        super(MusicList.class);
    }

    @Override
    public List<MusicAllListResponseDTO> findMusicListByPlayListId(Long playListId) {

        return from(musicList)
                .innerJoin(playList).on(musicList.playList.eq(playList))
                .where(playList.playListId.eq(playListId))
                .select(Projections.constructor(MusicAllListResponseDTO.class,
                        musicList.musicId, musicList.musicPlayOrder, musicList.musicUrl ))
                .fetch();
    }

    /**
     * 해당 재생목록의 마지막 번호를 리턴합니다
     * select music_play_order from music m inner join playlist p on m.play_list_id = p.play_list_id
     * where p.play_list_id = ?
     * order by b.music_play_order desc
     *
     * @param playListId 플레이리스트 고유번호
     * @return 음악목록 마지막 번호
     */
    @Override
    public Integer findByPlayListLastMusicPlayOrder(Long playListId) {
        try {  // NPE 떠서 추가.
            return from(musicList)
                    .innerJoin(playList).on(musicList.playList.eq(playList))
                    .where(playList.playListId.eq(playListId))
                    .orderBy(musicList.musicPlayOrder.desc())
                    .fetchFirst().getMusicPlayOrder();
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
