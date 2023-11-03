package com.pophory.pophoryapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "[Health Check] 서버 상태 확인 API")
public class HealthCheckController {

    @GetMapping("/health")
    @Operation(summary = "health check API", description = "항상 OK를 반환합니다.")
    public ResponseEntity<String> test() {return ResponseEntity.ok("OK");}

}
