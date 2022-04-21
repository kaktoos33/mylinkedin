package ir.manoosheh.mylinkedin.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import ir.manoosheh.mylinkedin.model.UserProfile;
import ir.manoosheh.mylinkedin.payload.graphql.request.UserProfileRequest;
import ir.manoosheh.mylinkedin.service.UserProfileService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Data
@Component
public class UserMutationResolver implements GraphQLMutationResolver {

    @Autowired
    final private UserProfileService userProfileService;

    @PreAuthorize("isAuthenticated()")
    public boolean addUserProfile(UserProfileRequest userProfileRequest) {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(userProfileRequest.getFirstName());
        userProfile.setLastName(userProfileRequest.getLastName());
        userProfile.setUsername(userProfileRequest.getUsername());
        userProfile.setDescription(userProfileRequest.getDescription());
        userProfile.setTitle(userProfileRequest.getTitle());
        userProfile.setCompany(userProfileRequest.getCompany());
        userProfile.setStartedAtMonth(userProfileRequest.getStartedAtMonth());
        userProfile.setStartedAtYear(userProfileRequest.getStartedAtYear());
        userProfile.setFinishedAtMonth(userProfileRequest.getFinishedAtMonth());
        userProfile.setFinishedAtYear(userProfileRequest.getFinishedAtYear());
        userProfileService.addUserProfile(userProfile);
        return true;
    }
}
