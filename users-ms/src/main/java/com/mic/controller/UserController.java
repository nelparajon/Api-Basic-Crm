package com.mic.controller;

import com.mic.dto.UserRequestDto;
import com.mic.dto.ApiResponse;
import com.mic.dto.UserResponseDto;
import com.mic.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
       List<UserResponseDto> users = userService.getAllUsers();
       ApiResponse<List<UserResponseDto>> response = new ApiResponse("Usuarios encontrados", users);
       return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto request) {
        UserResponseDto dto = userService.createUser(request);
        ApiResponse<UserResponseDto> response = new ApiResponse<>("Usuario creado correctamente", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{email}")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
            @PathVariable String email,
            @Valid @RequestBody UserRequestDto request) {

        UserResponseDto updatedUser = userService.updateUser(email, request);
        ApiResponse<UserResponseDto> response = new ApiResponse<>("Usuario actualizado correctamente", updatedUser);

        return ResponseEntity.ok(response);
    }

    /*@GetMapping("searchByFullName/{fullName}")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getUsersByFullName(@PathVariable String fullName) {
        String[] names = fullName.split("-");
        String name = names[0];
        String lastName = names[1];
        List<UserResponseDto> users = userService.findByFullName(name, lastName);
        return ResponseEntity.ok(new ApiResponse<>("Usuarios encontrados con ese nombre", users));
    }*/

    @GetMapping("/searchByEmail")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getUsersByEmail(@RequestParam String email) {
        List<UserResponseDto> users = userService.findByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>("Usuarios encontrados con ese email", users));
    }
}
