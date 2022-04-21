package ir.manoosheh.mylinkedin.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import ir.manoosheh.mylinkedin.payload.graphql.request.NewUserPostRequest;
import ir.manoosheh.mylinkedin.payload.graphql.response.SubmitResponse;
import ir.manoosheh.mylinkedin.service.PostCreationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class PostCreationMutationResolver implements GraphQLMutationResolver {

    @Autowired
    final PostCreationService postCreationService;

    @PreAuthorize("isAuthenticated()")
    public SubmitResponse newUserPost(NewUserPostRequest newUserPostRequest) throws Exception {
        return postCreationService.newUserPost(newUserPostRequest);
    }


}
