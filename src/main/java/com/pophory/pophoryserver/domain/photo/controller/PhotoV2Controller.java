package com.pophory.pophoryserver.domain.photo.controller;


import com.pophory.pophoryserver.domain.photo.PhotoService;
import com.pophory.pophoryserver.domain.photo.dto.request.PhotoAddV2RequestDto;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoAddResponseDto;
import com.pophory.pophoryserver.global.util.MemberUtil;
import com.pophory.pophoryserver.global.util.PhotoUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/photo")
@Tag(name = "[Photo] 네컷사진 관련 API (V2)")
public class PhotoV2Controller {

    private final PhotoService photoService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "사진 추가 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "사진 추가 성공"),
            @ApiResponse(responseCode = "400", description = "사진 추가 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> uploadPhoto(@RequestBody PhotoAddV2RequestDto request, Principal principal) throws URISyntaxException {
        PhotoAddResponseDto response =  photoService.addPhotoV2(request, MemberUtil.getMemberId(principal));
        return ResponseEntity.created(PhotoUtil.getURI(response.getPhotoId())).build();
    }
}
