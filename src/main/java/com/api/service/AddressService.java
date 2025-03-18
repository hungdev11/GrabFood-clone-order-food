package com.api.service;

import com.api.dto.request.AddressRequest;
import com.api.entity.Address;

public interface AddressService {
    long addNewAddress(AddressRequest addressRequest);
    public Address getAddressById(long id);
}
