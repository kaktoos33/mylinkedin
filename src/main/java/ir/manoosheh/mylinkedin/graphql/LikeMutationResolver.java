package ir.manoosheh.mylinkedin.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import ir.manoosheh.mylinkedin.model.UserPost;
import ir.manoosheh.mylinkedin.service.LikesService;
import ir.manoosheh.mylinkedin.service.PostCreationService;
import ir.manoosheh.mylinkedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LikeMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private final LikesService likesService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final PostCreationService postCreationService;

    @PreAuthorize("isAuthenticated()")
    public boolean likePost(Long postId) throws Exception {

//        User tmpUser = userService.getUserByID(userId);
        UserPost tmpUserPost = postCreationService.getUserPostById(postId);
        return likesService.likePost(tmpUserPost);
    }

}
