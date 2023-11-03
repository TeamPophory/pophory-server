package com.pophory.pophoryapi.studio.application;


import com.pophory.pophoryapi.studio.dto.StudioGetResponseDto;
import com.pophory.pophoryapi.studio.dto.StudioResponseDto;
import com.pophory.pophoryapi.studio.dto.StudioV2ResponseDto;
import com.pophory.pophorydomain.studio.infrastructure.StudioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudioService {
    private final StudioJpaRepository studioJpaRepository;

    @Transactional(readOnly = true)
    public StudioResponseDto findAll() {
        return StudioResponseDto.of(studioJpaRepository.findAll()
                .stream().filter(studio -> studio.getId() >= 0L)
                .collect(Collectors.toList()));
    }

    public StudioGetResponseDto findAllV2() {
        List<StudioV2ResponseDto> studios = studioJpaRepository.findAll().stream()
                .filter(studio -> studio.getId() != -1)
                .map(studio -> StudioV2ResponseDto.builder()
                        .id(studio.getId())
                        .name(studio.getName())
                        .build())
                .collect(Collectors.toList());

        return StudioGetResponseDto.of(studios);
    }
}