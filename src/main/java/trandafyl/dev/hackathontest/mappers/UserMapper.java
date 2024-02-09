package trandafyl.dev.hackathontest.mappers;

import org.mapstruct.Mapper;
import trandafyl.dev.hackathontest.dto.UserListResponse;
import trandafyl.dev.hackathontest.dto.UserResponse;
import trandafyl.dev.hackathontest.models.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserListResponse.PartialUserResponse userPartial);
    User toUser(UserResponse userPartial);
    UserListResponse.PartialUserResponse toUserPartialResponse(User user);
    UserResponse toUserResponse(User user);

    default UserListResponse toUserListResponse(List<User> users){
        var partialsUsers = users.stream().map(this::toUserPartialResponse).toList();

        return UserListResponse
                .builder()
                .response(partialsUsers)
                .recordsCount(partialsUsers.size())
                .build();
    }
}
