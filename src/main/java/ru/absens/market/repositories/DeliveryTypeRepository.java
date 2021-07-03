package ru.absens.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.absens.market.models.DeliveryType;

import java.util.List;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Long> {

    List<DeliveryType> findAll();
}