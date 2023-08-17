package com.pophory.pophoryserver.domain.studio;

import com.pophory.pophoryserver.config.TestConfig;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@DataJpaTest
@RequiredArgsConstructor
@Import(TestConfig.class)
public class StudioTest {

    @Autowired
    private StudioJpaRepository studioJpaRepository;

    private static final String NAME = "기본 스튜디오";
    private static final String IMAGE_URL = "https://localhost:8080/studio/studio_default.png";
    private static final String IMAGE_URL_2 = "https://localhost:8080/studio/studio_default2.png";

    @Test
    @DisplayName("스튜디오가 성공적으로 만들어진다.")
    @Transactional
    void successCreateStudio() {
        Studio studio = Studio.builder()
                .name(NAME)
                .imageUrl(IMAGE_URL)
                .build();
        Assertions.assertThat(studio.getName()).isEqualTo(NAME);
        Assertions.assertThat(studio.getImageUrl()).isEqualTo(IMAGE_URL);
    }

    @Test
    @DisplayName("스튜디오 이름이 중복될 경우 예외가 발생한다.")
    @Transactional
    void failCreateStudioWithDuplicatedName() {
        Studio studio = Studio.builder()
                .name(NAME)
                .imageUrl(IMAGE_URL)
                .build();
        studioJpaRepository.save(studio);
        Studio studio2 = Studio.builder()
                .name(NAME)
                .imageUrl(IMAGE_URL_2)
                .build();
        Assertions.assertThatThrownBy(() -> studioJpaRepository.saveAndFlush(studio2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}
