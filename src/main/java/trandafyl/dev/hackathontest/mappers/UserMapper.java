package trandafyl.dev.hackathontest.mappers;

import org.mapstruct.Mapper;
import trandafyl.dev.hackathontest.dto.UserPartialResponse;
import trandafyl.dev.hackathontest.dto.UserResponse;
import trandafyl.dev.hackathontest.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserPartialResponse userPartial);
    User toUser(UserResponse userPartial);
    UserPartialResponse toUserPartialResponse(User user);
    UserResponse toUserResponse(User user);
}
