package com.dj.server.api.websocket.controller;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.websocket.entity.ChatMessage;
import com.dj.server.api.websocket.repository.ChatMessageRepository;
import com.dj.server.api.websocket.service.ChatRoomService;
import com.dj.server.api.websocket.service.ChatService;
import com.dj.server.common.exception.member.MemberCrudErrorCode;
import com.dj.server.common.exception.member.MemberException;
import com.dj.server.common.exception.member.MemberPermitErrorCode;
import com.dj.server.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 실시간 채팅 컨트롤러
 *
 * @author Informix
 * @created 2021-08-23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pub")
public class ChatController {

    private final JwtUtil jwtUtil;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시지를 처리합니다
     */
    @GetMapping("/message/{roomId}")
    public List<ChatMessage> loadMessage(@PathVariable String roomId) {
        return chatMessageRepository.findAllByRoomIdOrderByTimenowDesc(roomId);
    }

    /**
     * 웹소켓으로 들어오는 메시지 발행 처리 -> 클라이언트에서는 /pub/chat/message로 발행 요청
     *
     * @param message pub으로 들어온 메시지
     * @param accessToken 액세스토큰
     */
    @MessageMapping("/chat/message")
    public void message(@RequestBody ChatMessage message, @Header("access_token") String accessToken) {

        if (!jwtUtil.isValidAccessToken(accessToken)) throw new MemberException(MemberPermitErrorCode.ACCESS_TOKEN_EXPIRED);

        Long memberId = jwtUtil.getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberCrudErrorCode.NOT_FOUND_MEMBER));
        message.setMemberNickName(member.getMemberNickName());

        // 채팅방 인원수 세팅
        message.setUserCount(chatRoomService.getUserCount(message.getRoomId()));

        // Websocket에 발행된 메시지를 redis로 발행(publish)
        chatService.sendChatMessage(message); // 메서드 일원화
        chatMessageRepository.save(message);
    }
}
