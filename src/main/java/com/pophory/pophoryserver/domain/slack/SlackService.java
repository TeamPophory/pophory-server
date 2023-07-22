package com.pophory.pophoryserver.domain.slack;

import com.pophory.pophoryserver.domain.slack.dto.SlackMessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class SlackService {

    @Value("${slack.webhook.url}")
    private String SLACK_WEBHOOK_URL;

    public void sendSignInAlert(String nickname) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(
                SLACK_WEBHOOK_URL,
                createSlackHttpRequest(nickname + "님이 회원가입을 완료했습니다."),
                String.class);
    }

    private HttpEntity<SlackMessageDto> createSlackHttpRequest(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; UTF-8");
        return new HttpEntity<>(SlackMessageDto.of(text), headers);
    }
}
