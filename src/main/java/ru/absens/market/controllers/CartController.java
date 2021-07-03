package ru.absens.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.absens.market.dtos.StringResponse;
import ru.absens.market.services.CartService;
import ru.absens.market.utils.Cart;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping
    public Cart getCart(Principal principal, @RequestParam String cartId) {
        if (principal != null) {
            cartId = principal.getName();
        }
        return cartService.getCurrentCart(cartId);
    }

    @GetMapping("/add")
    public void addToCart(Principal principal, @RequestParam String cartId, @RequestParam(name = "p") Long productId, @RequestParam(name = "q", defaultValue = "1") Integer quantity) {
        if (principal != null) {
            cartId = principal.getName();
        }
        cartService.addToCart(cartId, productId, quantity);
    }

    @PutMapping("/change")
    public BigDecimal changeProductQuantity(Principal principal, @RequestParam String cartId, @RequestParam(name = "p") Long productId, @RequestParam(name = "q", defaultValue = "1") Integer quantity) {
        if (principal != null) {
            cartId = principal.getName();
        }
        cartService.changeProductQuantity(cartId, productId, quantity);
        return cartService.getCurrentCart(cartId).getSum();
    }

    @GetMapping("/sum")
    public BigDecimal getCartSum(Principal principal, @RequestParam String cartId) {
        if (principal != null) {
            cartId = principal.getName();
        }
        return cartService.getCurrentCart(cartId).getSum();
    }

    @DeleteMapping("/rem-product")
    public Cart removeProduct(Principal principal, @RequestParam String cartId, @RequestParam(name = "p") Long productId) {
        if (principal != null) {
            cartId = principal.getName();
        }
        return cartService.removeProduct(cartId, productId);
    }

    @GetMapping("/clear")
    public Cart clearCart(Principal principal, @RequestParam String cartId) {
        if (principal != null) {
            cartId = principal.getName();
        }
        Cart cart = cartService.getCurrentCart(cartId);
        cart.clear();
        return cart;
    }

    @GetMapping("/generate")
    public StringResponse getNewCartId() {
        String uuid = null;
        do {
            uuid = UUID.randomUUID().toString();
        } while (cartService.isCartExists(uuid));
        return new StringResponse(uuid);
    }

    @GetMapping("/merge")
    public void mergeCarts(Principal principal, @RequestParam String cartId) {
        cartService.merge(principal.getName(), cartId);
    }
}
