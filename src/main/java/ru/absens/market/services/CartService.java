package ru.absens.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.absens.market.dtos.OrderItemDto;
import ru.absens.market.error_handling.ResourceNotFoundException;
import ru.absens.market.models.Product;
import ru.absens.market.utils.Cart;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final RedisTemplate<String, Object> redisTemplate;

    public Cart getCurrentCart(String cartId) {
        if (!redisTemplate.hasKey(cartId)) {
            redisTemplate.opsForValue().set(cartId, new Cart());
        }
        Cart cart = (Cart)redisTemplate.opsForValue().get(cartId);
        return cart;
    }

    public void save(String cartId, Cart cart) {
        redisTemplate.opsForValue().set(cartId, cart);
    }

    public void addToCart(String cartId, Long productId, Integer quantity) {
        Cart cart = getCurrentCart(cartId);
        List<OrderItemDto> items = cart.getItems();
        for (OrderItemDto orderItem : items) {
            if (orderItem.getProduct().getId().equals(productId)) {
                orderItem.incrementQuantity(quantity);
                cart.recalculate();
                save(cartId, cart);
                return;
            }
        }
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists id: " + productId + " (add to cart)"));
        OrderItemDto orderItem = new OrderItemDto(product);
        orderItem.incrementQuantity(quantity);
        items.add(orderItem);
        cart.recalculate();
        save(cartId, cart);
    }

    public void changeProductQuantity(String cartId, Long id, Integer quantity) {
        Cart cart = getCurrentCart(cartId);
        List<OrderItemDto> items = cart.getItems();
        for (OrderItemDto orderItem : items) {
            if (orderItem.getProduct().getId().equals(id)) {
                orderItem.changeQuantity(quantity);
                cart.recalculate();
                save(cartId, cart);
                return;
            }
        }
    }

    public Cart removeProduct(String cartId, Long productId) {
        Cart cart = getCurrentCart(cartId);
        cart.getItems().removeIf(p -> p.getProduct().getId().equals(productId));
        cart.recalculate();
        save(cartId, cart);
        return cart;
    }

    public boolean isCartExists(String cartId) {
        return redisTemplate.hasKey(cartId);
    }

    public void merge(String userCartId, String guestCartId) {
        Cart userCart = getCurrentCart(userCartId);
        Cart guestCart = getCurrentCart(guestCartId);
        userCart.merge(guestCart);
        save(userCartId, userCart);
        save(guestCartId, guestCart);
    }
}
