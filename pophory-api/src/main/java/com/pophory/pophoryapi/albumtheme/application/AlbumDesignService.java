package com.pophory.pophoryapi.albumtheme.application;


import com.pophory.pophoryapi.albumtheme.dto.response.AlbumDesignGetResponseDto;
import com.pophory.pophoryapi.albumtheme.dto.response.AlbumDesignsGetResponseDto;
import com.pophory.pophorydomain.albumtheme.infrastructure.AlbumDesignJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumDesignService {

    private final AlbumDesignJpaRepository albumDesignJpaRepository;

    @Transactional(readOnly = true)
    public AlbumDesignsGetResponseDto getAlbumDesignList() {
        return AlbumDesignsGetResponseDto.of(
                albumDesignJpaRepository.findAll()
                        .stream()
                        .map(AlbumDesignGetResponseDto::of)
                        .collect(Collectors.toList()));
    }
}
