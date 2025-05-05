package service;

import com.mic.dto.UserRequestDto;
import com.mic.dto.UserResponseDto;
import com.mic.model.User;
import com.mic.repository.UserRepository;
import com.mic.service.UserService;
import com.mic.utils.Role;
import com.mic.utils.UserMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveUser(){
        User user = User.builder()
                .firstName("Paco")
                .lastName("Gonzalez")
                .email("pg@example.es")
                .password("123456")
                .role(Role.ROLE_CEO).build();

        UserRequestDto userRequestDto = UserRequestDto.builder()
                .firstName("Paco")
                .lastName("Gonzalez")
                .email("pg@example.es")
                .password("123456")
                .role(Role.ROLE_CEO).build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .firstName("Paco")
                .lastName("Gonzalez")
                .email("pg@example.es")
                .role(Role.ROLE_CEO).build();

        when(userMapper.toEntity(userRequestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userResponseDto);

        UserResponseDto result = userService.createUser(userRequestDto);
        assertNotNull(result);
        assertEquals(userResponseDto.getEmail(), result.getEmail());
        verify(userRepository).save(user);
        verify(passwordEncoder).encode("123456");
    }

    @Test
    void testUpdateUser() {
        String email = "pg@example.es";

        // Usuario ya existente en BD
        User existingUser = User.builder()
                .id("1")
                .publicId("abc123")
                .firstName("Paco")
                .lastName("Gonzalez")
                .email(email)
                .password("encoded123")
                .role(Role.ROLE_SALES)
                .build();

        // DTO con nuevos datos
        UserRequestDto updateDto = UserRequestDto.builder()
                .firstName("Francisco")
                .lastName("Gonzalez")
                .password("123456")  // puede que se ignore si no se actualiza en el mapper
                .role(Role.ROLE_CEO)
                .build();

        // DTO de salida esperado
        UserResponseDto updatedResponseDto = UserResponseDto.builder()
                .firstName("Francisco")
                .lastName("Gonzalez")
                .email(email)
                .role(Role.ROLE_CEO)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        // Simulamos modificación del User con @MappingTarget
        doAnswer(invocation -> {
            UserRequestDto dto = invocation.getArgument(0);
            User user = invocation.getArgument(1);
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setRole(dto.getRole());
            return null;
        }).when(userMapper).updateUserFromDto(updateDto, existingUser);

        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(userMapper.toDTO(existingUser)).thenReturn(updatedResponseDto);

        UserResponseDto result = userService.updateUser(email, updateDto);

        // Verificaciones
        assertNotNull(result);
        assertEquals("Francisco", result.getFirstName());
        assertEquals(Role.ROLE_CEO, result.getRole());

        verify(userRepository).findByEmail(email);
        verify(userMapper).updateUserFromDto(updateDto, existingUser);
        verify(userRepository).save(existingUser);
        verify(userMapper).toDTO(existingUser);
    }
    @Test
    void testGetAllUsers() {
        List<User> users = List.of(
                User.builder().firstName("Paco").lastName("Gonzalez").email("pg@example.es").role(Role.ROLE_CEO).build(),
                User.builder().firstName("Ana").lastName("López").email("ana@example.es").role(Role.ROLE_SALES).build()
        );

        List<UserResponseDto> userDtos = List.of(
                UserResponseDto.builder().firstName("Paco").lastName("Gonzalez").email("pg@example.es").role(Role.ROLE_CEO).build(),
                UserResponseDto.builder().firstName("Ana").lastName("López").email("ana@example.es").role(Role.ROLE_SALES).build()
        );

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDTO(users.get(0))).thenReturn(userDtos.get(0));
        when(userMapper.toDTO(users.get(1))).thenReturn(userDtos.get(1));

        List<UserResponseDto> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Paco", result.get(0).getFirstName());
        assertEquals("Ana", result.get(1).getFirstName());

        verify(userRepository).findAll();
        verify(userMapper).toDTO(users.get(0));
        verify(userMapper).toDTO(users.get(1));
    }
    @Test
    void testFindByEmailSuccess() {
        String emailSearch = "pg";
        List<User> matchedUsers = List.of(
                User.builder().firstName("Paco").lastName("Gonzalez").email("pg@example.es").role(Role.ROLE_CEO).build()
        );

        List<UserResponseDto> expectedDtos = List.of(
                UserResponseDto.builder().firstName("Paco").lastName("Gonzalez").email("pg@example.es").role(Role.ROLE_CEO).build()
        );

        when(userRepository.findByEmailContainingIgnoreCase(emailSearch)).thenReturn(matchedUsers);
        when(userMapper.toDTO(matchedUsers.get(0))).thenReturn(expectedDtos.get(0));

        List<UserResponseDto> result = userService.findByEmail(emailSearch);

        assertEquals(1, result.size());
        assertEquals("Paco", result.get(0).getFirstName());

        verify(userRepository).findByEmailContainingIgnoreCase(emailSearch);
        verify(userMapper).toDTO(matchedUsers.get(0));
    }



}
