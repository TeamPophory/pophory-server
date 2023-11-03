package com.pophory.pophorydomain.photo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class PhotoSizeVO {

    private Integer width;
    private Integer height;
}
