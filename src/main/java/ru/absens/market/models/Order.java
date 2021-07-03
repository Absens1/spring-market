package ru.absens.market.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "delivery_type_id")
    private DeliveryType deliveryType;

    @ManyToOne
    @JoinColumn(name = "orders_shipping_info_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private OrderShippingInfo orderShippingInfo;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void setOrderShippingInfo(OrderShippingInfo orderShippingInfo) {
        this.orderShippingInfo = orderShippingInfo;
    }

    public void setOrderShippingInfo(UserShippingInfo userShippingInfo) {
        this.orderShippingInfo = new OrderShippingInfo();
        this.orderShippingInfo.setFirstName(userShippingInfo.getUser().getFirstName());
        this.orderShippingInfo.setLastName(userShippingInfo.getUser().getLastName());
        this.orderShippingInfo.setCountry(userShippingInfo.getCountry());
        this.orderShippingInfo.setCity(userShippingInfo.getCity());
        this.orderShippingInfo.setAddress(userShippingInfo.getAddress());
        this.orderShippingInfo.setZipCode(userShippingInfo.getZipCode());
    }
}
