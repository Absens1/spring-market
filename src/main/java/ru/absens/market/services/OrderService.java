package ru.absens.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.absens.market.dtos.OrderItemDto;
import ru.absens.market.error_handling.ResourceNotFoundException;
import ru.absens.market.models.*;
import ru.absens.market.repositories.OrderRepository;
import ru.absens.market.utils.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final DeliveryTypeService deliveryTypeService;
    private final UserShippingInfoService userShippingInfoService;
    private final OrderShippingInfoService orderShippingInfoService;
    private final CartService cartService;

    public List<Order> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    public Integer getOrdersCountByUser(User user) {
        return findAllByUser(user).size();
    }

    public Order findByIdAndUser(Long id, User user) {
        return orderRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Order doesn't exists: " + id));
    }

    @Transactional
    public Order createOrderForCurrentUser(User user, Long deliveryTypeId, String cartNumber) {
        Order order = new Order();
        order.setUser(user);
        DeliveryType deliveryType = deliveryTypeService.findById(deliveryTypeId);
        order.setDeliveryType(deliveryType);
        UserShippingInfo userShippingInfo = userShippingInfoService.findByUserId(user.getId()).get();
        Optional<OrderShippingInfo> orderShippingInfo = orderShippingInfoService.findByUserShippingInfo(userShippingInfo);
        if (orderShippingInfo.isPresent()) { // todo можно реализовать выбор из списка адресов, тогда уйдет необходимость в поиске по параметрам
            order.setOrderShippingInfo(orderShippingInfo.get());
        } else {
            order.setOrderShippingInfo(userShippingInfo);
        }
        Cart cart = cartService.getCurrentCart(user.getUsername()); // todo ERROR
        order.setPrice(cart.getSum().add(deliveryType.getPrice()));
        order.setCardNumber(cartNumber);
        // todo распутать этот кусок
        order.setItems(new ArrayList<>());
        for (OrderItemDto o : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(o.getQuantity());
            orderItem.setPricePerProduct(o.getPricePerProduct());
            orderItem.setPrice(o.getPrice());
            orderItem.setProduct(productService.findById(o.getProduct().getId()).get());
            order.getItems().add(orderItem);
        }
        order = orderRepository.save(order);
        cart.clear();
        cartService.save(user.getUsername(), cart);
        return order;
    }
}
