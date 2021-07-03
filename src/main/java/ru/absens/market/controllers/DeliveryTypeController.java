package ru.absens.market.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.absens.market.dtos.DeliveryTypeDto;
import ru.absens.market.services.DeliveryTypeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryTypeController {
    private final DeliveryTypeService deliveryTypeService;

    @GetMapping
    public List<DeliveryTypeDto> getAllDelivery() {
        return deliveryTypeService.findAll();
    }

    @GetMapping("/{id}")
    public DeliveryTypeDto getOneDeliveryById(@PathVariable Long id) {
        return new DeliveryTypeDto(deliveryTypeService.findById(id));
    }
}
