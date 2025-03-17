package ru.systems1221.whereMyCake.service.Impl;

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

    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }
}
