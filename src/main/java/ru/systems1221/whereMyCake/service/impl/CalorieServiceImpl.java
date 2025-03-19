package ru.systems1221.whereMyCake.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import ru.systems1221.whereMyCake.constant.GenderEnum;
import ru.systems1221.whereMyCake.entity.UserEntity;
import ru.systems1221.whereMyCake.exception.UserNotFoundException;
import ru.systems1221.whereMyCake.repository.UserRepository;
import ru.systems1221.whereMyCake.service.CalorieService;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalorieServiceImpl implements CalorieService {

    private static final float MALE_BASE_MULTIPLIER = 88.36f;
    private static final float MALE_WEIGHT_MULTIPLIER = 13.4f;
    private static final float MALE_HEIGHT_MULTIPLIER = 4.8f;
    private static final float MALE_AGE_MULTIPLIER = 5.7f;

    private static final float FEMALE_BASE_MULTIPLIER = 447.6f;
    private static final float FEMALE_WEIGHT_MULTIPLIER = 9.2f;
    private static final float FEMALE_HEIGHT_MULTIPLIER = 3.1f;
    private static final float FEMALE_AGE_MULTIPLIER = 4.3f;

    private final UserRepository userRepository;

    @Override
    public Float getUserCalorie(UUID userid) throws UserNotFoundException {

        Optional<UserEntity> userEntityOptional = userRepository.findById(userid);
        UserEntity user = userEntityOptional.orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userid));

        float calorie;
        if (user.getGender() == GenderEnum.M) {
            calorie = MALE_BASE_MULTIPLIER
                    + (MALE_WEIGHT_MULTIPLIER * user.getWeight())
                    + (MALE_HEIGHT_MULTIPLIER * user.getHeight())
                    - (MALE_AGE_MULTIPLIER * user.getAge());
        } else {
            calorie = FEMALE_BASE_MULTIPLIER
                    + (FEMALE_WEIGHT_MULTIPLIER * user.getWeight())
                    + (FEMALE_HEIGHT_MULTIPLIER * user.getHeight())
                    - (FEMALE_AGE_MULTIPLIER * user.getAge());
        }
        return calorie;
    }
}
