package com.pophory.pophoryserver.global.util;

import java.time.LocalDate;

public class PhotoUtil {

    public static String changeTakenAtToResponseFormat(LocalDate takenAt) {
            return takenAt.toString().substring(0, 10).replace("-", ".");

    }

   public static LocalDate changeRequestToTakenAt(String takenAt) {
        return LocalDate.parse(takenAt.replace(".", "-"));
    }
}
