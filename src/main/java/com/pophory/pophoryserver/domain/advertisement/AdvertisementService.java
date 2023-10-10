package com.pophory.pophoryserver.domain.advertisement;


import com.pophory.pophoryserver.domain.advertisement.dto.response.AdvertisementResponse;
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
