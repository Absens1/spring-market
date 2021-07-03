package ru.absens.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.absens.market.models.OrderShippingInfo;
import ru.absens.market.models.UserShippingInfo;
import ru.absens.market.repositories.OrderShippingInfoRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderShippingInfoService {
    private final OrderShippingInfoRepository orderShippingInfoRepository;

    public Optional<OrderShippingInfo> findByUserShippingInfo(UserShippingInfo u) {
        return orderShippingInfoRepository.findByFirstNameAndLastNameAndCountryAndCityAndAddressAndZipCode(u.getUser().getFirstName(), u.getUser().getLastName(), u.getCountry(), u.getCity(), u.getAddress(), u.getZipCode());
    }
}