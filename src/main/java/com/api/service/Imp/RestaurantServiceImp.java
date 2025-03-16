package com.api.service.Imp;

import com.api.dto.request.AddRestaurantRequest;
import com.api.dto.request.AddressRequest;
import com.api.model.Restaurant;
import com.api.repository.RestaurantRepository;
import com.api.service.AccountService;
import com.api.service.AddressService;
import com.api.service.RestaurantService;
import com.api.utils.RestaurantStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImp implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AccountService accountService;
    private final AddressService addressService;

    @Override
    @Transactional
    public long addRestaurant(AddRestaurantRequest request) {
        log.info("Add Restaurant");
        Restaurant newRestaurant = Restaurant.builder()
                .name(request.getName())
                .image(request.getImage())
                .openingHour(request.getOpeningHour())
                .closingHour(request.getClosingHour())
                .description(request.getDescription())
                .build();

        log.info("Create account");
        long accountId = accountService.addNewAccount(request.getUsername(), request.getPassword());

        log.info("Create address");
        long addressId = addressService.addNewAddress(AddressRequest.builder()
                        .ward(request.getAddress().getWard())
                        .district(request.getAddress().getDistrict())
                        .province(request.getAddress().getProvince())
                .build());

        log.info("Add Restaurant address and account");
        newRestaurant.setAccount(accountService.getAccountById(accountId));
        newRestaurant.setAddress(addressService.getAddressById(addressId));

        newRestaurant.setStatus(RestaurantStatus.ACTIVE);
        log.info("Persist Restaurant");
        return restaurantRepository.save(newRestaurant).getId();
    }
}
