package com.aleksej.mapper;

import com.aleksej.config.MapStructConfig;
import com.aleksej.domain.User;
import com.aleksej.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class, uses = CardInfoMapper.class)
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
    void updateEntityFromDto(UserDto dto, @MappingTarget User user);
}