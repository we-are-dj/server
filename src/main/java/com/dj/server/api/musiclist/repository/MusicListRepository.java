package com.dj.server.api.musiclist.repository;

import com.dj.server.api.musiclist.entity.MusicList;
import com.dj.server.api.playlist.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 재생될 음악리스트와 관련된 Repository
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 */
@Repository
public interface MusicListRepository extends JpaRepository<MusicList, Long>, MusicListQueryDSLRepository {

    List<MusicList> findByPlayList(PlayList playList);

    Optional<MusicList> findByMusicId(Long musicId);

    Optional<MusicList> findByMusicIdAndPlayList(Long musicId, PlayList playList);

    //해당 재생목록의 마지막번호를 가져오는 쿼리


}