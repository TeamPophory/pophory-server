package com.pophory.pophoryserver.domain.studio;

import com.pophory.pophoryserver.domain.studio.dto.StudioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudioService {
    private final StudioJpaRepository studioJpaRepository;

    @Transactional(readOnly = true)
    public StudioResponseDto findAll() {
        return StudioResponseDto.of(studioJpaRepository.findAll());
    }
}