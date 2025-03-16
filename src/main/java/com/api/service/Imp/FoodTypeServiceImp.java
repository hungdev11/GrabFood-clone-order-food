package com.api.service.Imp;

import com.api.dto.request.AddFoodTypeRequest;
import com.api.exception.AppException;
import com.api.exception.ErrorCode;
import com.api.model.FoodType;
import com.api.repository.FoodTypeRepository;
import com.api.service.FoodTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodTypeServiceImp implements FoodTypeService {
    private final FoodTypeRepository foodTypeRepository;

    @Override
    @Transactional
    public long addNewFoodType(String name) {
        if (foodTypeRepository.existsByName(name)) {
            log.info("FoodType with name " + name + " already exists");
            throw new AppException(ErrorCode.FOODTYPE_NAME_EXISTED);
        }
        return foodTypeRepository.save(FoodType.builder()
                .name(name)
                .build()).getId();
    }

    @Override
    public FoodType getFoodTypeByName(String name) {
        return foodTypeRepository.findByName(name).orElseThrow( () -> {
            log.info("FoodType with name " + name + " does not exist");
            return new AppException();
        });
    }
}
