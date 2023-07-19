package com.pophory.pophoryserver.domain.albumtheme;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlbumTheme {
    FRIEND("친구(friends)"),
    LOVE("사랑(love)"),
    ME("개인(me)"),
    FAMILY("가족(family)");

    private final String name;
}
