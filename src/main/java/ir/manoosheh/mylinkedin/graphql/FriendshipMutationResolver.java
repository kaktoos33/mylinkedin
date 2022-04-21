package ir.manoosheh.mylinkedin.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import ir.manoosheh.mylinkedin.service.FriendshipService;
import ir.manoosheh.mylinkedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FriendshipMutationResolver implements GraphQLMutationResolver {

    @Autowired
    final private FriendshipService friendshipService;
    @Autowired
    final private UserService userService;


    @PreAuthorize("isAuthenticated()")
    public boolean sendFriendshipRequest(Long userId) throws Exception {
        friendshipService.sendFriendshipRequest(userService.getUserByID(userId));

        return true;


    }

    @PreAuthorize("isAuthenticated()")
    public boolean acceptFriendshipRequest(Long userId) throws Exception {
        friendshipService.acceptFriendshipRequest(userService.getUserByID(userId));
        return true;
    }

    @PreAuthorize("isAuthenticated()")
    public boolean denyFriendshipRequest(Long userId) throws Exception {
        friendshipService.denyFriendshipRequest(userService.getUserByID(userId));
        return true;
    }


}
