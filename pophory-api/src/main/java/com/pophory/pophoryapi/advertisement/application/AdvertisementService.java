package com.pophory.pophoryapi.advertisement.application;


import com.pophory.pophoryapi.advertisement.dto.response.AdvertisementResponse;
import com.pophory.pophorydomain.advertisement.Advertisement;
import com.pophory.pophorydomain.advertisement.infrastructure.AdvertisementQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvertisementService {

    private final AdvertisementQueryRepository advertisementQueryRepository;

    public List<AdvertisementResponse> getAdvertisements(String os, String version) {
        List<Advertisement> advertisements = advertisementQueryRepository.findAllByOSAndVersion(os, version);
        return advertisements.stream()
                .map(AdvertisementResponse::of)
                .collect(Collectors.toList());
    }

}
