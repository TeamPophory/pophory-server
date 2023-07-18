package com.pophory.pophoryserver.domain.photo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
@Schema(description = "사진 전체 조회")
public class PhotoAllListResponseDto {

    List<PhotoAllListV2ResponseDto> photos;
}
