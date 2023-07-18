package com.pophory.pophoryserver.domain.version;


import com.pophory.pophoryserver.domain.version.dto.VersionGetResponseDto;
import com.pophory.pophoryserver.domain.version.dto.VersionListGetResponseDto;
import com.pophory.pophoryserver.domain.version.dto.VersionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceVersionService {

    private final ServiceVersionJpaRepository serviceVersionJpaRepository;

    @Transactional(readOnly = true)
    public VersionListGetResponseDto getVersion() {
        return VersionListGetResponseDto.of(
                serviceVersionJpaRepository.findAll()
                .stream()
                .map(VersionGetResponseDto::of)
                .collect(Collectors.toList()));
    }
    
    @Transactional
    public void updateServiceVersion(VersionUpdateRequestDto request) {
        serviceVersionJpaRepository.findByOsType(request.getOs())
                .ifPresentOrElse(
                        serviceVersion -> serviceVersion.updateVersion(request.getVersion()),
                        () -> serviceVersionJpaRepository.save(ServiceVersion.builder()
                                .osType(request.getOs())
                                .version(request.getVersion())
                                .build())
                );
    }
}
