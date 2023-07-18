package com.pophory.pophoryserver.domain.studio;

import com.pophory.pophoryserver.domain.photo.Photo;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoGetResponseDto;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoListGetResponseDto;
import com.pophory.pophoryserver.domain.studio.dto.StudioGetResponseDto;
import com.pophory.pophoryserver.domain.studio.dto.StudioResponseDto;
import com.pophory.pophoryserver.domain.studio.dto.StudioV2ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
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