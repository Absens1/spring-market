package ru.absens.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.absens.market.models.*;
import ru.absens.market.repositories.UserShippingInfoRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserShippingInfoService {
    private final UserShippingInfoRepository userShippingInfoRepository;

    public Optional<UserShippingInfo> findByUserId(Long id) {
        return userShippingInfoRepository.findById(id);
    }

    public UserShippingInfo save(UserShippingInfo userShippingInfo) {
        return userShippingInfoRepository.save(userShippingInfo);
    }
}