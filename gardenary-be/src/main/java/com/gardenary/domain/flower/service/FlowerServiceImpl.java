package com.gardenary.domain.flower.service;

import com.gardenary.domain.current.entity.GrowingPlant;
import com.gardenary.domain.current.repostiory.GrowingPlantRepository;
import com.gardenary.domain.exp.entity.Exp;
import com.gardenary.domain.exp.repository.ExpRepository;
import com.gardenary.domain.flower.dto.*;
import com.gardenary.domain.flower.dto.response.*;
import com.gardenary.domain.flower.entity.Flower;
import com.gardenary.domain.flower.entity.MyFlower;
import com.gardenary.domain.flower.entity.QuestionAnswer;
import com.gardenary.domain.flower.mapper.QuestionAnswerMapper;
import com.gardenary.domain.flower.repository.FlowerRepository;
import com.gardenary.domain.flower.repository.MyFlowerRepository;
import com.gardenary.domain.flower.repository.QuestionAnswerRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.domain.user.repository.UserRepository;
import com.gardenary.global.error.exception.FlowerApiException;
import com.gardenary.global.error.exception.GrowingPlantApiException;
import com.gardenary.global.error.model.FlowerErrorCode;
import com.gardenary.global.error.model.GrowingPlantErrorCode;
import com.gardenary.global.properties.ConstProperties;
import com.gardenary.infra.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlowerServiceImpl implements FlowerService{

    private final GrowingPlantRepository growingPlantRepository;
    private final MyFlowerRepository myFlowerRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final ExpRepository expRepository;
    private final FlowerRepository flowerRepository;
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final ConstProperties constProperties;
    private static final String[] flowerIdx = {"0_0", "0_1", "0_2", "1_0", "1_1", "2_0", "2_1", "2_2", "3_0", "3_1", "4_0", "5_0", "6_0", "7_0", "8_0", "9_0", "10_0", "10_1", "11_0", "12_0", "13_0", "14_0", "14_1", "14_2", "14_3", "14_4", "15_0", "16_0", "16_1", "17_0", "18_0", "19_0", "19_1", "20_0", "20_1", "20_2", "20_3", "21_0", "21_1", "21_2", "21_3", "21_4", "21_5", "22_0", "23_0", "23_1", "23_2", "24_0", "25_0", "25_1", "25_2", "25_3", "26_0", "26_1", "26_2", "26_3", "26_4", "26_5", "27_0"};

    @Override
    @Transactional
    public AnswerCompleteResponseDto createAnswer(User user, QuestionAnswerDto questionAnswerDto) {
        
        AnswerCompleteResponseDto result = new AnswerCompleteResponseDto();
        //시간 판별,추후에 프론트에서 작성시작 시간을 받으면 코드 처리 하는 것으로 수정 가능성있음.
        LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime startTime;
        LocalDateTime endTime;
        if(time.getHour() >= 3){
            startTime = time.withHour(3).withMinute(0).withSecond(0);
            endTime = startTime.plusDays(1).minusSeconds(1);
        } else{
            startTime = time.withHour(3).withMinute(0).withSecond(0).minusDays(1);
            endTime = startTime.plusDays(1).minusSeconds(1);
        }
        if(questionAnswerDto.isOver()){
            time.withHour(2).withMinute(59).withSecond(59);
            startTime = time.withHour(3).withMinute(0).withSecond(0).minusDays(1);
            endTime = startTime.plusDays(1).minusSeconds(1);
        }
        //현재 식물 가져오기
        GrowingPlant growingPlant = growingPlantRepository.findByUser(user);
        if(growingPlant == null) {
            throw new GrowingPlantApiException(GrowingPlantErrorCode.GROWING_PLANT_NOT_FOUND);
        }
        //오늘 이미 작성했는지 확인하기(가장 최근 것 가져와서 했는지 가져와서 확인, 추후에 함수로 빼기)
        List<QuestionAnswer> list = questionAnswerRepository.findAllByMyFlower_UserOrderByCreatedAtDesc(user);
        if(list.size() != 0){
            QuestionAnswer lastQA = list.get(0);
            LocalDateTime lastTime = lastQA.getCreatedAt();
//            if(lastTime.isAfter(startTime) && lastTime.isBefore(endTime)) {
//                throw new FlowerApiException(FlowerErrorCode.TODAY_ALREADY_WRITE);
//            }
            //연속 작성 확인
//            if(lastTime.isAfter(startTime.minusDays(1)) && lastTime.isBefore(endTime.minusDays(1))){
//                growingPlant.modifyAnswerDays(growingPlant.getAnswerDays()+1);
//            } else{
//                growingPlant.modifyAnswerDays(1);
//            }
            growingPlant.modifyAnswerDays(growingPlant.getAnswerDays()+1);
            if(growingPlant.getAnswerDays()%2 == 0) {
                result.modifyIsItem(true);
            } else{
                result.modifyIsItem(false);
            }
        }else{
            growingPlant.modifyAnswerDays(1);
        }

        //총 답변 갯수 수정
        growingPlant.modifyAnswerCnt(growingPlant.getAnswerCnt() + 1);
        //유저 id로 현재 꽃, 꽃 아이디 찾기
        MyFlower currentFlower = growingPlant.getMyFlower();
        if(currentFlower == null){
            throw new FlowerApiException(FlowerErrorCode.MY_FLOWER_NOT_FOUND);
        }
        //캐시에서 현재 질문아이디 가져오기
        String ques = redisService.getStringValue(user.getKakaoId());
        int questionId = Integer.parseInt(ques);
        //Dto에 유저 정보, 질문 정보, 꽃 정보 시간, 질문 번호
        QuestionAnswerDto saveQuestionAnswerDto = QuestionAnswerDto.builder()
                .flowerId(currentFlower.getFlower().getId())
                .createdAt(time)
                .myFlowerId(currentFlower.getId())
                .questionId(questionId)
                .userId(user.getId())
                .content(questionAnswerDto.getContent())
                .questionNum(growingPlant.getAnswerCnt())
                .build();
        //내용 저장
        questionAnswerRepository.save(QuestionAnswerMapper.mapper.toEntity(saveQuestionAnswerDto));
        //경험치 기록 추가
        Exp exp = Exp.builder()
                .expAmount(constProperties.getExpFlower())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .diaryId(currentFlower.getId())
                .type(true)
                .user(user)
                .build();
        expRepository.save(exp);
        //경험치 증가(Redis)
        String flowerExp = redisService.getStringValue(user.getKakaoId()+"flowerExp");
        int totalExp = Integer.parseInt(flowerExp) + constProperties.getExpFlower();
        redisService.setValue(user.getKakaoId()+"flowerExp", totalExp+"");
        //return 값 만들기
        result.modifyTotalExp(totalExp);
        return result;
    }

    @Override
    public QuestionAnswerListResponseDto getOneFlowerAnswerList(User user, int myFlowerId) {
        //해당 유저와 내 꽃 아이디에 대해 조회 (에러까지 확인)
        MyFlower myFlower = myFlowerRepository.findById(myFlowerId).orElseThrow(()-> new FlowerApiException(FlowerErrorCode.MY_FLOWER_NOT_FOUND));
        //해당 유저가 조회하는 것이 아닌 경우
        if(!myFlower.getUser().getKakaoId().equals(user.getKakaoId())) {
            return null;
        }

        //엔티티 리스트
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAllByMyFlowerAndMyFlower_UserOrderByCreatedAtDesc(myFlower, user);

        //리턴한 Dto 리스트로 만들기
        List<QuestionAnswerResponseDto> result = makeAnswerDtoList(questionAnswerList);
        return QuestionAnswerListResponseDto.builder()
                .questionAnswerResponseDtoList(result)
                .build();
    }

    @Override
    public QuestionAnswerListResponseDto getAllFlowerAnswerList(User user) {
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAllByMyFlower_UserOrderByCreatedAtDesc(user);
        List<QuestionAnswerResponseDto> result = makeAnswerDtoList(questionAnswerList);
        return QuestionAnswerListResponseDto.builder()
                .questionAnswerResponseDtoList(result)
                .build();
    }


    private  List<QuestionAnswerResponseDto> makeAnswerDtoList(List<QuestionAnswer> questionAnswerList){
        List<QuestionAnswerResponseDto> result = new ArrayList<>();

        for (QuestionAnswer questionAnswer : questionAnswerList){
            QuestionAnswerResponseDto questionAnswerResponseDto = QuestionAnswerResponseDto.builder()
                    .createdAt(questionAnswer.getCreatedAt())
                    .question(questionAnswer.getQuestion().getContent())
                    .content(questionAnswer.getContent())
                    .questionNum(questionAnswer.getQuestionNum())
                    .build();
            result.add(questionAnswerResponseDto);
        }
        return result;
    }

    @Override
    public CompleteFlowerInfoResponseDto createNewFlower(User user) {
        //꽃의 총 경험치를 가져오기
        String total = redisService.getStringValue(user.getKakaoId()+"flowerExp");
        int totalExp = Integer.parseInt(total);
        if(totalExp == 0 || (totalExp % constProperties.getExpLevelup()) != 0) {
            throw new FlowerApiException(FlowerErrorCode.NOT_ENOUGH_EXP);
        }
        //바꾸기 전의 현재 꽃에 doneAt 추가
        GrowingPlant current = growingPlantRepository.findByUser(user);
        MyFlower doneFlower = myFlowerRepository.findById(current.getMyFlower().getId()).orElseThrow(()-> new FlowerApiException(FlowerErrorCode.MY_FLOWER_NOT_FOUND));
        doneFlower.modifyDoneAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        myFlowerRepository.save(doneFlower);
        Flower newFlower = randomFlower();
        //새로운 꽃 MyFlower에 넣어주기
        MyFlower myFlower = MyFlower.builder()
                .flower(newFlower)
                .user(user)
                .build();
        myFlowerRepository.save(myFlower);
        //Current 새로운 꽃으로 바꿔주기
        current.modifyMyFlower(myFlower);
        growingPlantRepository.save(current);
        return CompleteFlowerInfoResponseDto.builder()
                .assetId(doneFlower.getFlower().getAssetId())
                .color(doneFlower.getFlower().getColor())
                .name(doneFlower.getFlower().getName())
                .build();
    }

    @Override
    public FlowerListResponseDto getFlowerList(User user) {
        //전체 리스트를 하나 만들기(모든 색상 정보가 들어가고 flag가 false인 flowerList)
        List<Flower> flowers = flowerRepository.findAllByOrderByNameAscIdAsc();
        List<FlowerResponseDto> flowerList = new ArrayList<>();
        Flower firstFlower = flowers.get(0);
        List<FlowerColorResponseDto> list = new ArrayList<>();
        list.add(FlowerColorResponseDto.builder()
                .color(firstFlower.getColor())
                .flag(false)
                .build());
        flowerList.add(FlowerResponseDto.builder()
                .name(firstFlower.getName())
                .isGet(false)
                .meaning(firstFlower.getMeaning())
                .bloom(firstFlower.getBloom())
                .content(firstFlower.getContent())
                .assetId(firstFlower.getAssetId())
                .colorList(list)
                .build());
        for (int i = 1; i < flowers.size(); i++) {
            Flower flower = flowers.get(i);
            String name = flower.getName();
            FlowerColorResponseDto flowerColorResponseDto = FlowerColorResponseDto.builder()
                    .color(flower.getColor())
                    .flag(false)
                    .build();
            FlowerResponseDto beforeFlowerResponse = flowerList.get(flowerList.size()-1);
            if(name.equals(beforeFlowerResponse.getName())){
                List<FlowerColorResponseDto> colorList = beforeFlowerResponse.getColorList();
                colorList.add(flowerColorResponseDto);
                beforeFlowerResponse.modifyColorList(colorList);
            } else {
                List<FlowerColorResponseDto> colorList = new ArrayList<>();
                colorList.add(flowerColorResponseDto);
                FlowerResponseDto flowerResponseDto = FlowerResponseDto.builder()
                        .name(flower.getName())
                        .isGet(false)
                        .meaning(flower.getMeaning())
                        .bloom(flower.getBloom())
                        .content(flower.getContent())
                        .assetId(flower.getAssetId())
                        .colorList(colorList)
                        .build();
                flowerList.add(flowerResponseDto);
            }
        }
        //유저가 가진 MyFlower 다 받아오기 (flowerId 순서대로)
        List<MyFlower> myFlowerList = myFlowerRepository.findAllByUser(user);
        //그중 현재 MyFlower는 제외하고 하나씩 flag true로 변경하기
        Set<String> set = new HashSet<>();
        for (MyFlower myFlower : myFlowerList) {
            if(myFlower.getDoneAt() == null) {
                continue;
            }
            Flower flower = myFlower.getFlower();
            String str = flower.getId();
            set.add(str);
        }
        Iterator<String> iter = set.iterator();
        for (int i = 0; i < set.size() ; i++) {
            String flowerId = iter.next();
            int flowerType = Integer.parseInt(flowerId.split("_")[0]);
            int colorType = Integer.parseInt(flowerId.split("_")[1]);
            flowerList.get(flowerType).modifyIsGet(true);
            flowerList.get(flowerType).getColorList().get(colorType).modifyFlag(true);
        }
        return FlowerListResponseDto.builder()
                .flowerList(flowerList)
                .build();
    }

    public Flower randomFlower() {
        int num = (int)(Math.random()*constProperties.getFlowerSize() + 1);
        Flower flower = flowerRepository.findById(flowerIdx[num]);
        if(flower == null) {
            throw new FlowerApiException(FlowerErrorCode.FLOWER_NOT_FOUND);
        }
        return flower;
    }

    @Scheduled(cron = "0 0 3 * * *")
    protected void questionReset(){
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            int num = (int)(Math.random()*constProperties.getQuestionSize() + 1);
            redisService.setValue(user.getKakaoId(), num + "");
        }
    }

}
