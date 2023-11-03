package com.pophory.pophoryexternal.s3;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UploadType {
    PHOTO("images/"),
    PROFILE("profile/");

    private final String name;

}
