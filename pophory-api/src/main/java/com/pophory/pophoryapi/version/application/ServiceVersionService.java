package com.pophory.pophoryapi.version.application;

import com.pophory.pophoryapi.version.dto.request.VersionUpdateRequestDto;
import com.pophory.pophoryapi.version.dto.response.VersionGetResponseDto;
import com.pophory.pophoryapi.version.dto.response.VersionListGetResponseDto;
import com.pophory.pophorydomain.version.ServiceVersion;
import com.pophory.pophorydomain.version.infrastructure.ServiceVersionJpaRepository;
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
