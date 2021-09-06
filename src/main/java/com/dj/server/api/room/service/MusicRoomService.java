package com.dj.server.api.room.service;


import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.room.entity.MusicRoom;
import com.dj.server.api.room.entity.MusicRoomRepository;
import com.dj.server.api.room.model.dto.response.MusicRoomSaveResponseDTO;
import com.dj.server.api.room.model.dto.request.MusicRoomSaveRequestDTO;
import com.dj.server.common.exception.common.BizException;
import com.dj.server.common.exception.member.MemberCrudErrorCode;
import com.dj.server.common.exception.room.RoomCrudErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MusicRoomService {

    //나중에 DB로 옮길값
    private final static int CHAT_ROOM_LIMIT = 5;


   // 채팅방에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    //구독 처리
    private final RedisSubscriber redisSubscriber;

    //Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String , Object> redisTemplate;

    //Redis Hash Description
    //Redis Hash <Key , HashKey , HashValue>
    // <CHAT_ROOM , CHAT_ROOM_ID, chatRoomDTO> // CHAT_ROOM 이라는 키값에 ROOM_ID HashKey , CHAT ROOM INFO 이렇게 들어감.
    private HashOperations<String, Long, MusicRoomSaveResponseDTO> operations;

    private Map<String, ChannelTopic> topics;

    //Repository
    private final MusicRoomRepository musicRoomRepository;
    private final MemberRepository memberRepository;


    @PostConstruct
    private void init() {
        operations = redisTemplate.opsForHash(); // Redis Hash 를 쉽게 Serialize / DeSerialize 시켜주는 레디스 interface
        topics = new HashMap<>();
    }

    /**
     *
     * 모든 채팅방을 조회합니다.
     *
     * @return
     */
    public List<MusicRoomSaveResponseDTO> findAllRoom() {
        return operations.values(CHAT_ROOMS);
    }


    /**
     *
     * 채팅방 key + CHAT_ROOM_ID 로 하나 의 채팅방의 정보만 조회합니다.
     *
     * @param id
     * @return
     */
    public MusicRoomSaveResponseDTO findByRoomId(String id) {
        try {
            return operations.get(CHAT_ROOMS, id);
        } catch (NullPointerException e) { // NPE 에러 대비.
            throw new BizException(RoomCrudErrorCode.NOT_FOUND);
        }
    }

    /**
     *
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis Hash 에 저장
     *
     * @param memberId
     * @param musicRoomSaveRequestDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MusicRoomSaveResponseDTO createChatRoom(Long memberId , MusicRoomSaveRequestDTO musicRoomSaveRequestDTO) {

        //회원 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BizException(MemberCrudErrorCode.NOT_FOUND_MEMBER));

        if(musicRoomRepository.countByRoomMaster(member) >= CHAT_ROOM_LIMIT) throw new BizException(RoomCrudErrorCode.BAD_REQUEST);

        //방 정보 rdb 에 저장
        MusicRoom musicRoom = musicRoomSaveRequestDTO.toEntity(member);

        MusicRoomSaveResponseDTO musicRoomSaveResponseDTO = MusicRoomSaveResponseDTO.builder().roomId(musicRoom.getRoomId()).roomName(musicRoom.getRoomName()).build();
        operations.put(CHAT_ROOMS, musicRoomSaveResponseDTO.getRoomId(), musicRoomSaveResponseDTO);
        return musicRoomSaveResponseDTO;
    }

    /**
     *
     * 서버가 여러대면 각 서버별로 토픽을 저장합니다.
     *
     * @param roomId
     */
    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if(topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListenerContainer.addMessageListener(redisSubscriber,topic);
            topics.put(roomId, topic);
        }
    }

    /**
     *
     * 채널 관련 데이터를 불러옵니다.
     *
     * @param roomId
     * @return
     */
    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }


}
