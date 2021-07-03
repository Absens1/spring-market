package ru.absens.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.absens.market.models.OrderShippingInfo;

import java.util.Optional;

@Repository
public interface OrderShippingInfoRepository extends JpaRepository<OrderShippingInfo, Long> {
    //String firstName, String lastName, String country, String city, String address, Integer zipCode
    Optional<OrderShippingInfo> findByFirstNameAndLastNameAndCountryAndCityAndAddressAndZipCode(String firstName, String lastName, String country, String city, String address, Integer zipCode);
}
