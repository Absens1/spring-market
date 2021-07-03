package ru.absens.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.absens.market.dtos.DeliveryTypeDto;
import ru.absens.market.error_handling.ResourceNotFoundException;
import ru.absens.market.models.DeliveryType;
import ru.absens.market.repositories.DeliveryTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryTypeService {
    private final DeliveryTypeRepository deliveryTypeRepository;

    public List<DeliveryTypeDto> findAll() {
        return deliveryTypeRepository.findAll().stream().map(DeliveryTypeDto::new).collect(Collectors.toList());
    }

    public DeliveryType findById(Long id) {
        return deliveryTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Delivery doesn't exists: " + id));
    }
}
