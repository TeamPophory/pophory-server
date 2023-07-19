package com.pophory.pophoryserver.domain.albumtheme;

import com.pophory.pophoryserver.domain.albumtheme.dto.response.AlbumDesignGetResponseDto;
import com.pophory.pophoryserver.domain.albumtheme.dto.response.AlbumDesignListGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumDesignService {

    private final AlbumDesignJpaRepository albumDesignJpaRepository;

    @Transactional(readOnly = true)
    public AlbumDesignListGetResponseDto getAlbumDesignList() {
        return AlbumDesignListGetResponseDto.of(
                albumDesignJpaRepository.findAll()
                        .stream()
                        .map(AlbumDesignGetResponseDto::of)
                        .collect(Collectors.toList()));
    }
}
