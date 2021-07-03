package ru.absens.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.absens.market.models.User;
import ru.absens.market.models.UserShippingInfo;

import java.util.Optional;

@Repository
public interface UserShippingInfoRepository extends JpaRepository<UserShippingInfo, Long> {
    Optional<UserShippingInfo> findByUser(User user);
}
