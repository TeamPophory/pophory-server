package com.pophory.pophoryserver.domain.studio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioJpaRepository extends JpaRepository<Studio, Long> {

    Optional<Studio> findByName(String name);
}
