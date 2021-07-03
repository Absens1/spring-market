package ru.absens.market.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.absens.market.dtos.OrderDto;
import ru.absens.market.models.User;
import ru.absens.market.services.OrderService;
import ru.absens.market.services.UserService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    @Transactional
    public List<OrderDto> getAllOrdersForCurrentUser(Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        return orderService.findAllByUser(user).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @PostMapping
    public OrderDto createNewOrder(Principal principal, @RequestParam Long deliveryTypeId, @RequestParam String cardNumber) {
        User user = userService.findByUsername(principal.getName()).get();
        return new OrderDto(orderService.createOrderForCurrentUser(user, deliveryTypeId, cardNumber));
    }

    @GetMapping("/{id}")
    @Transactional
    public OrderDto getOrderByIdForCurrentUser(Principal principal, @PathVariable Long id) {
        User user = userService.findByUsername(principal.getName()).get();
        return new OrderDto(orderService.findByIdAndUser(id, user));
    }

    @GetMapping("/count")
    public Integer getOrdersCount(Principal principal) {
        if (principal != null) {
            User user = userService.findByUsername(principal.getName()).get();
            return orderService.getOrdersCountByUser(user);
        }
        return 0;
    }
}
