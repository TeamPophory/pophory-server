package com.pophory.pophoryserver.domain.member.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoAuthService extends SocialService {

    @Value("${jwt.KAKAO_URL}")
    private String KAKAO_URL;
    @Override
    public String login(String socialAccessToken) {
        return getKakaoData(socialAccessToken);
    }

    private String getKakaoData(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity<JSONArray> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> responseData = restTemplate.postForEntity(KAKAO_URL, httpEntity, Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(responseData.getBody(), Map.class).get("id").toString();
    }
}
