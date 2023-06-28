package com.pophory.pophoryserver.domain.studio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudioService {
    private final StudioJpaRepository studioJpaRepository;

    @Transactional
    public StudioResponseDTO findAll() {
        List<Studio> studios = studioJpaRepository.findAll();

        return StudioResponseDTO.of(studios);
    }
}
