package com.api.service;

import com.api.dto.request.AddressRequest;
import com.api.model.Account;
import com.api.model.Address;

public interface AddressService {
    long addNewAddress(AddressRequest addressRequest);
    public Address getAddressById(long id);
}
