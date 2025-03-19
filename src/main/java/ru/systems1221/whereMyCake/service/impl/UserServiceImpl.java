package ru.systems1221.whereMyCake.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import ru.systems1221.whereMyCake.entity.UserEntity;
import ru.systems1221.whereMyCake.repository.UserRepository;
import ru.systems1221.whereMyCake.service.UserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final int AGE_MIN = 14;
    private static final int AGE_MAX = 100;
    private static final float WEIGHT_MIN = 30f;
    private static final float WEIGHT_MAX = 222f;
    private static final int HEIGHT_MIN = 100;
    private static final int HEIGHT_MAX = 222;


    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {

        if (user.getAge() <= AGE_MIN || user.getAge() > AGE_MAX) {
            throw new IllegalArgumentException("Parameter age must be between 14 and 100");
        } else if (user.getWeight() <= WEIGHT_MIN || user.getWeight() > WEIGHT_MAX) {
            throw new IllegalArgumentException("Parameter weight must be between 30 and 222");
        } else if (user.getHeight() <= HEIGHT_MIN || user.getHeight() > HEIGHT_MAX) {
            throw new IllegalArgumentException("Parameter height must be between 100 and 222");
        }
        log.info("Successfully created User with id: {}", user.getId());
        return userRepository.save(user);
    }
}
