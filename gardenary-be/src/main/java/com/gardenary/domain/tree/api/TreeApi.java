package com.gardenary.domain.tree.api;

import com.gardenary.domain.tree.dto.DiaryDto;
import com.gardenary.domain.tree.dto.MyTreeDto;
import com.gardenary.domain.tree.service.TreeService;
import com.gardenary.global.common.properties.ResponseProperties;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.common.response.MessageResponse;
import com.gardenary.global.config.security.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tree")
@RequiredArgsConstructor
@Slf4j
public class TreeApi {
    private final TreeService treeService;
    private final ResponseProperties responseProperties;

    @PostMapping("")
    public ResponseEntity<MessageResponse> createMyTree(
            @AuthenticationPrincipal UserDetail userDetail) {
        return null;
    }

    @GetMapping("")
    public ResponseEntity<DtoResponse<List<MyTreeDto>>> getMyTreeList(
            @AuthenticationPrincipal UserDetail userDetail) {
        List<MyTreeDto> result = treeService.getMyTreeList(userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }

    @GetMapping("/diary")
    public ResponseEntity<DtoResponse<DiaryDto>> getDiary(
            @RequestParam(name = "date") LocalDateTime date,
            @AuthenticationPrincipal UserDetail userDetail) {
        DiaryDto result = treeService.getDiary(date, userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }

    @GetMapping("/diary/{myTreeId}")
    public ResponseEntity<DtoResponse<List<DiaryDto>>> getDiaryList(
            @PathVariable("myTreeId") int myTreeId,
            @AuthenticationPrincipal UserDetail userDetail) {
        List<DiaryDto> result = treeService.getDiaryList(myTreeId, userDetail.getUser());
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), result));
        }
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
    }

}