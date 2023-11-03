package com.pophory.pophorycommon.advice;


import com.pophory.pophorycommon.exception.AlbumLimitExceedException;
import com.pophory.pophorycommon.exception.BadRequestException;
import com.pophory.pophorycommon.exception.S3UploadException;
import com.pophory.pophorycommon.exception.SelfApproveException;
import com.pophory.pophorycommon.response.CodeResponse;
import com.pophory.pophorycommon.response.ResponseCode;
import com.pophory.pophoryexternal.slack.SlackService;
import io.sentry.Sentry;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


import java.io.IOException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    private final SlackService slackService;

    @Value("${slack.channel.monitor}")
    private String SLACK_CHANNEL_MONITOR;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> handleBadRequestException(final BadRequestException e) {
        Sentry.captureException(e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleEntityNotFoundException(final EntityNotFoundException e) {
        Sentry.captureException(e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Void> handleIOException(final IOException e) {
        Sentry.captureException(e);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(S3UploadException.class)
    public ResponseEntity<Void> handleS3UploadException(final S3UploadException e) {
        Sentry.captureException(e);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Void> handleIllegalStateException(final IllegalStateException e) {
        Sentry.captureException(e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        Sentry.captureException(e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(final IllegalArgumentException e) {
        Sentry.captureException(e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        Sentry.captureException(e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Void> handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException e) {
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).build();
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Void> handleEntityExistsException(final EntityExistsException e) {
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(final Exception e) {
        Sentry.captureException(e);
        slackService.sendMessage(SLACK_CHANNEL_MONITOR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(AlbumLimitExceedException.class)
    public ResponseEntity<CodeResponse> handleAlbumLimitExceedException(final AlbumLimitExceedException e) {
        Sentry.captureException(e);
        return ResponseEntity.badRequest().body(new CodeResponse(ResponseCode.ALBUM_LIMIT_EXCEED.getCode()));
    }

    @ExceptionHandler(SelfApproveException.class)
    public ResponseEntity<CodeResponse> handleSelfApproveException(final SelfApproveException e) {
        Sentry.captureException(e);
        return ResponseEntity.badRequest().body(new CodeResponse(ResponseCode.SELF_APPROVE.getCode()));
    }
}
