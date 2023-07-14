package com.pophory.pophoryserver.domain.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.pophory.pophoryserver.domain.fcm.dto.FcmDto;
import com.pophory.pophoryserver.domain.fcm.dto.request.FcmRequestDto;
import com.pophory.pophoryserver.domain.member.Member;
import com.pophory.pophoryserver.domain.member.MemberJpaRepository;
import com.pophory.pophoryserver.domain.fcm.dto.MessageDto;
import com.pophory.pophoryserver.domain.fcm.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FcmService {

    @Value("${fcm.api.url}")
    private String FCM_API_URL;

    @Value("${fcm.google.api}")
    private String GOOGLE_API_URL;

    @Value("${fcm.file-path}")
    private String FIREBASE_CONFIG_PATH;


    private final MemberJpaRepository memberJpaRepository;

    public void sendFcm(Long receiverId, FcmRequestDto request) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(
                FCM_API_URL,
                createHttpRequest(request.getTitle(), request.getBody(),
                getMemberById(receiverId).getFcmToken()),
                Object.class);
    }

    private HttpEntity<FcmDto> createHttpRequest(String title, String body, String token) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getAccessToken());
        headers.add("Accept", "application/json; UTF-8");
        return new HttpEntity<>(FcmDto.of(new MessageDto(new NotificationDto(title, body) , token)), headers);
    }

    private Member getMemberById(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));
    }
    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
                .createScoped(List.of(GOOGLE_API_URL));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}