package com.mike.demorestfulservice.mapper;

import com.mike.demorestfulservice.dto.CommentDto;
import com.mike.demorestfulservice.dto.TaskCommentsDto;
import com.mike.demorestfulservice.dto.TaskDto;
import com.mike.demorestfulservice.dto.UserDto;
import com.mike.demorestfulservice.entity.Comment;
import com.mike.demorestfulservice.entity.Task;
import com.mike.demorestfulservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EntityMapper {
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    TaskDto toTaskDto(Task task);

    TaskCommentsDto toTaskCommentDto(Task task);

    CommentDto toCommentDto(Comment comment);

    Comment toComment(CommentDto commentDto);
}
