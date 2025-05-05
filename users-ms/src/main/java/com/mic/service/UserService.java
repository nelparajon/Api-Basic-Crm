package com.mic.service;

import com.mic.dto.UserRequestDto;
import com.mic.exception.UserEmailNotFoundException;
import com.mic.exception.UserNotFoundException;
import com.mic.model.User;
import com.mic.dto.UserResponseDto;
import com.mic.repository.UserRepository;
import com.mic.utils.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(UserRequestDto dto) {
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    public UserResponseDto updateUser(String email, UserRequestDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserEmailNotFoundException(email));

        //Solo se actualizan los campos presentes en el dto
        userMapper.updateUserFromDto(dto, user);

        //Guardamos el user actualizado en la base de datos como entity
        User updatedUser = userRepository.save(user);

        //Retornamos el DTO con los datos actualizados
        return userMapper.toDTO(updatedUser);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public List<UserResponseDto> findByEmail(String email) {
        return Optional.of(userRepository.findByEmailContainingIgnoreCase(email))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new UserNotFoundException("No se encontró ningún usuario con ese email"))
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }
}
