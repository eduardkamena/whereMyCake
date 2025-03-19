package ru.systems1221.whereMyCake.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.systems1221.whereMyCake.constant.AimEnum;
import ru.systems1221.whereMyCake.constant.GenderEnum;
import ru.systems1221.whereMyCake.entity.UserEntity;
import ru.systems1221.whereMyCake.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class UserServiceImplTest {

    private static final String USER_NAME = "Иван Вкомандухотелов";
    private static final String USER_EMAIL = "ivan@mail.ru";
    private static final int USER_AGE = 25;
    private static final float USER_WEIGHT = 70f;
    private static final int USER_HEIGHT = 180;

    private static final int USER_AGE_INCORRECT = 10;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUser() {
        // given
        UserEntity user = new UserEntity();
        user.setName(USER_NAME);
        user.setEmail(USER_EMAIL);
        user.setGender(GenderEnum.M);
        user.setAge(USER_AGE);
        user.setWeight(USER_WEIGHT);
        user.setHeight(USER_HEIGHT);
        user.setAim(AimEnum.WEIGHT_LOSS);

        // when
        when(userRepository.save(user)).thenReturn(user);

        UserEntity result = userService.createUser(user);

        // then
        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldThrowsWhenCreateUser() {
        // given
        UserEntity user = new UserEntity();
        user.setAge(USER_AGE_INCORRECT);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
    }
}
