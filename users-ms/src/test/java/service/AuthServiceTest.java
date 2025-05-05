package service;


import com.mic.dto.AuthRequest;
import com.mic.dto.AuthResponse;
import com.mic.dto.UserResponseDto;
import com.mic.model.User;
import com.mic.repository.UserRepository;
import com.mic.service.AuthService;
import com.mic.service.JwtService;
import com.mic.utils.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import com.mic.utils.Role;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest{

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private AuthService authService;
    @BeforeEach
    void setUp(){
        User user = User.builder()
                .firstName("James")
                .email("jh@example.es")
                .password("123456")
                .build();
    }
    @Test
    void authenticationTest() {
        // Arrange
        String email = "jg@example.es";
        String password = "123456";
        AuthRequest authRequest = new AuthRequest(email, password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.ROLE_ADMIN);
        user.setId("jh1234");

        String expectedToken = "fake-jwt-token";
        UserResponseDto userDto = new UserResponseDto();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(anyMap(), any())).thenReturn("fake-jwt-token");
        when(userMapper.toDTO(user)).thenReturn(userDto);

        // Act
        AuthResponse response = authService.authentication(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals(expectedToken, response.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(email);
        verify(jwtService).generateToken(anyMap(), eq(user));
    }
}

