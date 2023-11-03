package com.pophory.pophoryapi.advertisement.dto.response;

import com.pophory.pophorydomain.advertisement.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdvertisementResponse {
    private String adId;
    private String adName;

    public static AdvertisementResponse of(Advertisement advertisement) {
        return new AdvertisementResponse(advertisement.getAdId(), advertisement.getAdName());
    }
}
