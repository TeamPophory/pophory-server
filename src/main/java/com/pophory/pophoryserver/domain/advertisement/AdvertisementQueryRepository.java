package com.pophory.pophoryserver.domain.advertisement;


import com.pophory.pophoryserver.domain.advertisement.dto.response.AdvertisementResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.pophory.pophoryserver.domain.advertisement.QAdvertisement.advertisement;

@Repository
@RequiredArgsConstructor
public class AdvertisementQueryRepository {

    private static final String ANDRIOD_OS = "android";
    private static final String IOS_OS = "ios";

    private final JPAQueryFactory queryFactory;

    public List<Advertisement> findAllByOSAndVersion(String os, String version) {
        switch (os) {
            case ANDRIOD_OS:
                return queryFactory.selectFrom(advertisement)
                        .where(advertisement.androidVersion.eq(version))
                        .fetch();
            case IOS_OS:
                return queryFactory.selectFrom(advertisement)
                        .where(advertisement.iOSVersion.eq(version))
                        .fetch();
        }
        return new ArrayList<>();
    }

}
