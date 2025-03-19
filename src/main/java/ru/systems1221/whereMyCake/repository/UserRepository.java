package ru.systems1221.whereMyCake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.systems1221.whereMyCake.entity.UserEntity;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link UserEntity}.
 * Предоставляет стандартные методы для работы с пользователями.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
