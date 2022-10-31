package com.gardenary.domain.flower.api;

import com.gardenary.domain.flower.dto.AnswerCompleteDto;
import com.gardenary.domain.flower.dto.QuestionAnswerDto;
import com.gardenary.domain.flower.service.FlowerService;
import com.gardenary.domain.user.entity.Role;
import com.gardenary.domain.user.entity.User;
import com.gardenary.global.common.response.DtoResponse;
import com.gardenary.global.properties.ResponseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/flower")
@RequiredArgsConstructor
public class FlowerApi {

    private final FlowerService flowerService;
    private final ResponseProperties responseProperties;


    //추후 user쪽 추가, 경험치 return으로 변겅
    @PostMapping("/answer")
    public ResponseEntity<DtoResponse> createAnswer(@RequestBody QuestionAnswerDto questionAnswerDto) {
        User user = new User(UUID.randomUUID(),"카카오", Role.USER);
        AnswerCompleteDto result = flowerService.createAnswer(user, questionAnswerDto);
        if(result == null) {
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getFail(), null));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(DtoResponse.of(HttpStatus.OK, responseProperties.getSuccess(), result));
        }
    }

}
