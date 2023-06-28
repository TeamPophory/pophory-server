package com.pophory.pophoryserver.domain.studio;

import com.pophory.pophoryserver.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioJpaRepository extends JpaRepository<Studio, Long> {
}
