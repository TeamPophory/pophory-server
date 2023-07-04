package com.pophory.pophoryserver.global.advice;

import com.pophory.pophoryserver.global.exception.PayloadTooLargeException;
import com.pophory.pophoryserver.global.exception.S3UploadException;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@RestControllerAdvice
public class ControllerExceptionHandler {

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

    @ExceptionHandler(PayloadTooLargeException.class)
    public ResponseEntity<Void> handleEntityIsTooLargeException(final PayloadTooLargeException e) {
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).build();
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Void> handleEntityIsTooLargeException(final EntityExistsException e) {
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(final Exception e) {
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
