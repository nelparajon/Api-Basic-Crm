package com.mic.utils;

import com.mic.dto.UserRequestDto;
import com.mic.model.User;
import com.mic.dto.UserResponseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequestDto dto);

    UserResponseDto toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserRequestDto dto, @MappingTarget User user);

    @AfterMapping
    default void setDefaults(@MappingTarget User user, UserRequestDto dto) {
        if (dto.getRole() == null) {
            user.setRole(Role.ROLE_SALES);
        }
    }

}
