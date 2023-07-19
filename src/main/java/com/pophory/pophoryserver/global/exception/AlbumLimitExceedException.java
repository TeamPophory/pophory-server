package com.pophory.pophoryserver.global.exception;

public class AlbumLimitExceedException extends RuntimeException {
        public AlbumLimitExceedException(String message) {
            super(message);
        }
}
