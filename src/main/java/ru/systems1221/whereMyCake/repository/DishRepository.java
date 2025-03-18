package ru.systems1221.whereMyCake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.systems1221.whereMyCake.entity.DishEntity;

import java.util.UUID;

@Repository
public interface DishRepository extends JpaRepository<DishEntity, UUID> {
}
