package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.UserTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public List<UserTO> findAllUsersBasicInfo() {
        return userRepository.findAll().stream()
                .map(user -> new UserTO(user.getId(), user.getFirstName()))
                .collect(Collectors.toList());
    }
    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    @Override
    public List<UserDto> findUsersByEmailFragment(String emailFragment) {
        return userRepository.findByEmailContainingIgnoreCase(emailFragment).stream()
                .map(user -> new UserDto(user.getId(), null,null,null, user.getEmail()))
                .collect(Collectors.toList());
    }
    public List<UserDto> findUsersOlderThan(Integer age) {
        LocalDate cutoffDate = LocalDate.now().minusYears(age);
        return userRepository.findUsersOlderThanAge(cutoffDate).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

}