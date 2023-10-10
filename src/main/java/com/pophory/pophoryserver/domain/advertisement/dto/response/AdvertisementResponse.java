package com.pophory.pophoryserver.domain.advertisement.dto.response;

import com.pophory.pophoryserver.domain.advertisement.Advertisement;
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
