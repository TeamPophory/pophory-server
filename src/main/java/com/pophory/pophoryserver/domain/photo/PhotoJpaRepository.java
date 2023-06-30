package com.pophory.pophoryserver.domain.photo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoJpaRepository extends JpaRepository<Photo, Long> {
}
