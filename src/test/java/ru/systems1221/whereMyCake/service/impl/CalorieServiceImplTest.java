package ru.systems1221.whereMyCake.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.systems1221.whereMyCake.constant.GenderEnum;
import ru.systems1221.whereMyCake.entity.UserEntity;
import ru.systems1221.whereMyCake.exception.UserNotFoundException;
import ru.systems1221.whereMyCake.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

class CalorieServiceImplTest {

    private static final float MALE_BASE_MULTIPLIER = 88.36f;
    private static final float MALE_WEIGHT_MULTIPLIER = 13.4f;
    private static final float MALE_HEIGHT_MULTIPLIER = 4.8f;
    private static final float MALE_AGE_MULTIPLIER = 5.7f;

    private static final float FEMALE_BASE_MULTIPLIER = 447.6f;
    private static final float FEMALE_WEIGHT_MULTIPLIER = 9.2f;
    private static final float FEMALE_HEIGHT_MULTIPLIER = 3.1f;
    private static final float FEMALE_AGE_MULTIPLIER = 4.3f;

    private static final float MALE_WEIGHT = 70f;
    private static final int MALE_HEIGHT = 180;
    private static final int MALE_AGE = 25;

    private static final float FEMALE_WEIGHT = 60f;
    private static final int FEMALE_HEIGHT = 160;
    private static final int FEMALE_AGE = 30;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CalorieServiceImpl calorieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetUserCalorieForMale() throws UserNotFoundException {
        // given
        UUID userId = UUID.randomUUID();
        UserEntity user = new UserEntity();
        user.setGender(GenderEnum.M);
        user.setWeight(MALE_WEIGHT);
        user.setHeight(MALE_HEIGHT);
        user.setAge(MALE_AGE);

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        float expectedCalorie = MALE_BASE_MULTIPLIER
                + (MALE_WEIGHT_MULTIPLIER * MALE_WEIGHT)
                + (MALE_HEIGHT_MULTIPLIER * MALE_HEIGHT)
                - (MALE_AGE_MULTIPLIER * MALE_AGE);
        float actualCalorie = calorieService.getUserCalorie(userId);

        // then
        assertEquals(expectedCalorie, actualCalorie, 0.01);
    }

    @Test
    void shouldGetUserCalorieForFemale() throws UserNotFoundException {
        // given
        UUID userId = UUID.randomUUID();
        UserEntity user = new UserEntity();
        user.setGender(GenderEnum.F);
        user.setWeight(FEMALE_WEIGHT);
        user.setHeight(FEMALE_HEIGHT);
        user.setAge(FEMALE_AGE);

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        float expectedCalorie = FEMALE_BASE_MULTIPLIER
                + (FEMALE_WEIGHT_MULTIPLIER * FEMALE_WEIGHT)
                + (FEMALE_HEIGHT_MULTIPLIER * FEMALE_HEIGHT)
                - (FEMALE_AGE_MULTIPLIER * FEMALE_AGE);
        float actualCalorie = calorieService.getUserCalorie(userId);

        // then
        assertEquals(expectedCalorie, actualCalorie, 0.01);
    }

    @Test
    void shouldGetUserCalorieIfUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> calorieService.getUserCalorie(userId));
    }
}
