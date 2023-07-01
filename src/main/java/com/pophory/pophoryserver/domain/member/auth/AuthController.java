package com.pophory.pophoryserver.domain.member.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<Void> signOut(@RequestHeader Long memberId) {
        authService.signOut(memberId);
        return ResponseEntity.noContent().build();
    }
}
