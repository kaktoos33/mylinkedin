package ir.manoosheh.mylinkedin.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import ir.manoosheh.mylinkedin.payload.graphql.request.LoginRequest;
import ir.manoosheh.mylinkedin.payload.graphql.request.SignupRequest;
import ir.manoosheh.mylinkedin.payload.graphql.request.UserProfileRequest;
import ir.manoosheh.mylinkedin.payload.graphql.response.LoginResponse;
import ir.manoosheh.mylinkedin.payload.graphql.response.SignupResponse;
import ir.manoosheh.mylinkedin.payload.graphql.response.SubmitResponse;
import ir.manoosheh.mylinkedin.service.AuthService;
import ir.manoosheh.mylinkedin.service.ProfileCreationService;
import ir.manoosheh.mylinkedin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@RequiredArgsConstructor
@Component
public class AuthMutationResolver implements GraphQLMutationResolver {


    @Autowired
    final UserService userService;
    @Autowired
    final AuthService authService;
    @Autowired
    final ProfileCreationService profileCreationService;

    public AuthMutationResolver(UserService userService, AuthService authService, ProfileCreationService profileCreationService) {
        this.userService = userService;
        this.authService = authService;
        this.profileCreationService = profileCreationService;
    }


    //    @PreAuthorize("isAnonymous()")
    public SignupResponse signup(SignupRequest signupRequest) throws Exception /*throws IsExistException*/ {
        return userService.save(signupRequest);

    }

    //    @PreAuthorize("isAnonymous()")
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        return authService.login(loginRequest);
    }


//    @PreAuthorize("isAuthenticated()")
//    public SubmitResponse companySignup( CompanySignupRequest companySignupRequest){
//        return profileCreationService.companySignup(companySignupRequest);
//    }

    //    @PreAuthorize("isAuthenticated()")
    public SubmitResponse userSignup(UserProfileRequest userProfileRequest) {
        return profileCreationService.userSignup(userProfileRequest);
    }
}