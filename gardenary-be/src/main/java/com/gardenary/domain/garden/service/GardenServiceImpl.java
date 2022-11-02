package com.gardenary.domain.garden.service;

import com.gardenary.domain.flower.entity.MyFlower;
import com.gardenary.domain.flower.repository.MyFlowerRepository;
import com.gardenary.domain.garden.entity.Garden;
import com.gardenary.domain.garden.repository.GardenRepository;
import com.gardenary.domain.garden.response.GardenFlowerResponseDto;
import com.gardenary.domain.garden.response.GardenItemResponseDto;
import com.gardenary.domain.garden.response.GardenListResponseDto;
import com.gardenary.domain.garden.response.GardenTreeResponseDto;
import com.gardenary.domain.item.entity.MyItem;
import com.gardenary.domain.item.repository.MyItemRepository;
import com.gardenary.domain.tree.entity.MyTree;
import com.gardenary.domain.tree.repository.MyTreeRepository;
import com.gardenary.domain.user.entity.User;
import com.gardenary.domain.user.repository.UserRepository;
import com.gardenary.global.error.exception.FlowerApiException;
import com.gardenary.global.error.exception.TreeApiException;
import com.gardenary.global.error.exception.UserApiException;
import com.gardenary.global.error.model.FlowerErrorCode;
import com.gardenary.global.error.model.TreeErrorCode;
import com.gardenary.global.error.model.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GardenServiceImpl implements GardenService{

    private final UserRepository userRepository;
    private final GardenRepository gardenRepository;
    private final MyFlowerRepository myFlowerRepository;
    private final MyTreeRepository myTreeRepository;
    private final MyItemRepository myItemRepository;
    @Override
    public GardenListResponseDto getGardenInfo(String userId) {
        //유저 찾기, 오류 체크까지
        User user = userRepository.findById(UUID.randomUUID()).orElseThrow(() -> new UserApiException(UserErrorCode.USER_NOT_FOUND));
        //해당 유저의 정원 정보 리스트 조회, 3종류의 리스트 생성
        List<Garden> gardenList = gardenRepository.findAllByUser(user);
        List<GardenFlowerResponseDto> flowerDtoList = new ArrayList<>();
        List<GardenTreeResponseDto> treeDtoList = new ArrayList<>();
        List<GardenItemResponseDto> itemDtoList = new ArrayList<>();
        GardenListResponseDto gardenListResponseDto = new GardenListResponseDto();
        //리스트 돌면서 type에 따라 myFlower/myTree/myItem통해서 Flower/Tree/Item assetId 찾기 (이 과정에서 각각 있는지 없는지 오류 체크)
        for (Garden garden : gardenList){
            if(garden.getType() == 1){
                MyFlower myFlower = myFlowerRepository.findById(garden.getObjectId());
                if(myFlower == null){
                    throw new FlowerApiException(FlowerErrorCode.MY_FLOWER_NOT_FOUND);
                }
                GardenFlowerResponseDto gardenFlowerResponseDto = GardenFlowerResponseDto.builder()
                        .start(myFlower.getCreatedAt())
                        .end(myFlower.getDoneAt())
                        .x(garden.getX())
                        .z(garden.getZ())
                        .myFlowerId(myFlower.getId())
                        .assetId(myFlower.getFlower().getAssetId())
                        .build();
                flowerDtoList.add(gardenFlowerResponseDto);
            } else if (garden.getType() == 2) {
                MyTree myTree = myTreeRepository.findById(garden.getObjectId()).orElseThrow(() -> new TreeApiException(TreeErrorCode.MY_TREE_NOT_FOUND));
                GardenTreeResponseDto gardenTreeResponseDto = GardenTreeResponseDto.builder()
                        .start(myTree.getCreatedAt())
//                        .end(myTree.getDoneAt())
                        .x(garden.getX())
                        .z(garden.getZ())
                        .myTreeId(myTree.getId())
                        .assetId(myTree.getTree().getAssetId())
                        .build();
                treeDtoList.add(gardenTreeResponseDto);
            } else if(garden.getType() == 3) {
                //수정 예정
                MyItem myItem = myItemRepository.findById(garden.getObjectId()).orElseThrow();
                GardenItemResponseDto gardenItemResponseDto = GardenItemResponseDto.builder()
                        .x(garden.getX())
                        .z(garden.getZ())
                        .myItemId(myItem.getId())
                        .assetId(myItem.getItem().getAssetId())
                        .build();
                itemDtoList.add(gardenItemResponseDto);
            }
        }
        return GardenListResponseDto.builder()
                .flowerList(flowerDtoList)
                .treeList(treeDtoList)
                .itemList(itemDtoList)
                .build();
    }
}
