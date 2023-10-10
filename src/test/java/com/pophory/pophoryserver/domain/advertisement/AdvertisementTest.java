package com.pophory.pophoryserver.domain.advertisement;


import com.pophory.pophoryserver.config.TestConfig;
import com.pophory.pophoryserver.fixture.advertisement.AdvertisementFixture;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@RequiredArgsConstructor
@Import(TestConfig.class)
public class AdvertisementTest {

    @Autowired
    private final AdvertisementJpaRepository advertisementJpaRepository;

    @DisplayName("광고 객체가 성공적으로 만들어진다.")
    @Test
    void test() {
        // given
        Advertisement advertisement = AdvertisementFixture.createAdvertisementFixture();
        // when
        advertisementJpaRepository.save(advertisement);
        // then
    }
}
