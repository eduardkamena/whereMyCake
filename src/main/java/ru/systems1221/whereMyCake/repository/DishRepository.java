package ru.systems1221.whereMyCake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.systems1221.whereMyCake.entity.DishEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface DishRepository extends JpaRepository<DishEntity, UUID> {

    @Query("SELECT d FROM dishes d WHERE d.users.id = :userId AND d.dateTime BETWEEN :start AND :end")
    List<DishEntity> findByUsersIdAndDateTimeBetween(
            @Param("userId") UUID userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
