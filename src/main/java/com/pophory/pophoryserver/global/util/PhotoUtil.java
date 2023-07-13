package com.pophory.pophoryserver.global.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PhotoUtil {

    public static String changeTakenAtToResponseFormat(LocalDate takenAt) {
            return takenAt.toString().substring(0, 10).replace("-", ".");

    }

   public static LocalDate changeRequestToTakenAt(String takenAt) {
        return LocalDate.parse(takenAt.replace(".", "-"));
    }

   public static URI getURI(Long photoId) {
        return ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{photoId}")
            .buildAndExpand(photoId)
            .toUri();
   }
}
