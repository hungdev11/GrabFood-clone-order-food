package com.api.mapper.Imp;

import com.api.dto.response.OrderResponse;
import com.api.entity.Order;
import com.api.mapper.OrderMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImp implements OrderMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OrderResponse toOrderResponse(Order order) {
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setCartDetails(order.getCartDetails());
        return  orderResponse;
    }
}
