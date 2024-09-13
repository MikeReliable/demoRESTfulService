package com.mike.demorestfulservice.mapper;

import com.mike.demorestfulservice.dto.UserDto;
import com.mike.demorestfulservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EntityMapper {
    UserDto toDto(User user);
    User toUser (UserDto userDto);
}
