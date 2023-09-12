package com.pophory.pophoryserver.domain.album;

import com.pophory.pophoryserver.config.TestConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@RequiredArgsConstructor
@Import(TestConfig.class)
public class AlbumTest {

    @Nested
    @DisplayName("앨범 생성 테스트")
    public class AlbumRegisterTest {

        @Test
        @DisplayName("앨범이 성공적으로 만들어진다.")
        void successCreateAlbum() {
            Album album = Album.builder()
                    .title("기본 앨범")
                    .imageUrl("https://localhost:8080/album/album_default.png")
                    .photoLimit(15)
                    .build();
        }
    }
}
