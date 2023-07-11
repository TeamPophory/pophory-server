package com.pophory.pophoryserver.global.advice;

import com.pophory.pophoryserver.global.exception.BadRequestException;
import com.pophory.pophoryserver.global.exception.S3UploadException;
import io.sentry.Sentry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@RestControllerAdvice
public class ControllerExceptionHandler {

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
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
