package ru.rodionov.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionov.dto.UserDTO;
import ru.rodionov.dto.request.UpdateProfileRequest;
import ru.rodionov.mapper.UserMapper;
import ru.rodionov.model.User;
import ru.rodionov.service.UserService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserDTO getUserProfile(UUID id) {
        return userMapper.toUserProfile(userService.getById(id));
    }

    @Transactional
    public UserDTO patchProfile(UUID id, UpdateProfileRequest request) {
        User user = userService.getById(id);
        userMapper.toUpdateEntity(user, request);
        return userMapper.toUserProfile(userService.save(user));
    }

    public List<UserDTO> getUsers() {
        return userMapper.toDto(userService.getUsers());
    }
}
