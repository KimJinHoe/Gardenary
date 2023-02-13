package com.gardenary.domain.profile.api;

import com.gardenary.domain.avatar.dto.AvatarDto;
import com.gardenary.domain.avatar.dto.response.AvatarResponseDto;
import com.gardenary.domain.profile.dto.ProfileDto;
import com.gardenary.domain.profile.dto.response.ProfileResponseDto;
import com.gardenary.domain.profile.service.ProfileService;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.common.response.MessageResponse;
import com.gardenary.global.config.security.UserDetail;
import com.gardenary.global.properties.ResponseProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/profile")
public class ProfileApi {

    private final ProfileService profileService;
    private final ResponseProperties responseProperties;

    @GetMapping("")
    public ResponseEntity<DtoResponse<ProfileResponseDto>> getProfile(@AuthenticationPrincipal UserDetail userDetail) {

        ProfileResponseDto result = profileService.getProfile(userDetail.getUser());

        if (result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }

    @GetMapping("/avatar")
    public ResponseEntity<DtoResponse<List<AvatarResponseDto>>> getAvatar(@AuthenticationPrincipal UserDetail userDetail) {

        List<AvatarResponseDto> result = profileService.getAvatar(userDetail.getUser());

        if (result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }

    @PostMapping("/nickname")
    public ResponseEntity<MessageResponse> modifyNickname(@AuthenticationPrincipal UserDetail userDetail, @RequestBody ProfileDto profileDto) {

        boolean result = profileService.modifyNickname(userDetail.getUser(), profileDto);

        if (!result) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess()));
    }

    @PostMapping("/avatar")
    public ResponseEntity<MessageResponse> modifyAvatar(@AuthenticationPrincipal UserDetail userDetail, @RequestBody AvatarDto avatarDto) {

        boolean result = profileService.modifyAvatar(userDetail.getUser(), avatarDto);

        if (!result) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess()));
    }
}
