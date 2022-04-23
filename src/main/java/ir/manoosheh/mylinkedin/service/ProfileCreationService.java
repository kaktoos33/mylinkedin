package ir.manoosheh.mylinkedin.service;

import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.model.UserProfile;
import ir.manoosheh.mylinkedin.payload.graphql.request.UserProfileRequest;
import ir.manoosheh.mylinkedin.payload.graphql.response.SubmitResponse;
import ir.manoosheh.mylinkedin.repository.UserProfileRepository;
import ir.manoosheh.mylinkedin.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class ProfileCreationService {
    @Autowired
    final UserRepository userRepository;
    @Autowired
    final UserProfileRepository userProfileRepository;
    @Autowired
    final UserService userService;


    public SubmitResponse userSignup(UserProfileRequest userSignupRequest) {
        try {
            String email = userService.getUsername();
            System.out.println(email);
            if (Objects.equals(email, "anonymousUser")) {
                log.error("Someone tried making user profile without signing in");
                return new SubmitResponse(false, "User hasn't logged in yet");
            }

            User user = userRepository.findByEmail(email).orElse(null);

       /* if (user.getIsCompany()) {
            log.error("aaaaaaaaaaaaaaaaaaaaaaaa");
            log.error("Company account '{}' tried to make a user profile", user.getUsername());
            return new SubmitResponse(false, "User is a company account");
        }
        */

            if (userProfileRepository.existsByUser(user)) {
                log.error("Username '{}' already exists.", userSignupRequest.getUsername());
                user.setEnabled(true);
                userRepository.save(user);
                return new SubmitResponse(false, "Username is in use");
            }

            UserProfile profile = new UserProfile();
            profile.setUser(user);
            profile.setFirstName(userSignupRequest.getFirstName());
            profile.setLastName(userSignupRequest.getLastName());
            profile.setUsername(userSignupRequest.getUsername());
            profile.setUserPosts(new ArrayList<>());
            if (!Objects.isNull(userSignupRequest.getTitle())) {
                profile.setTitle(userSignupRequest.getTitle());
                profile.setCompany(userSignupRequest.getCompany());
                profile.setStartedAtMonth(userSignupRequest.getStartedAtMonth());
                profile.setStartedAtYear(userSignupRequest.getStartedAtYear());
                profile.setFinishedAtMonth(userSignupRequest.getFinishedAtMonth());
                profile.setFinishedAtYear(userSignupRequest.getFinishedAtYear());
            }

            if (!Objects.isNull(userSignupRequest.getDescription())) {
                profile.setDescription(userSignupRequest.getDescription());
            }

            userProfileRepository.save(profile);

            user.setEnabled(true);
            user.setUserProfile(profile);
//            user.setCompanyProfile(null);
            userRepository.save(user);

            log.info("User Profile added: {}", profile.getUsername());
            return new SubmitResponse(true, profile.getUsername());
        } catch (Exception e) {
            return new SubmitResponse(false, e.getLocalizedMessage());
        }
    }
//    public SubmitResponse companySignup(CompanySignupRequest companySignupRequest){
//        try {
//            String email = userService.getUsername();
//            if(Objects.equals(email, "anonymousUser")) {
//                log.error("Someone tried making company profile without signing in");
//                return new SubmitResponse(false, "User hasn't logged in yet");
//            }
//
//            User user = userRepository.findByUsernameIs(email);
//
//            CompanyProfile profile = new CompanyProfile();
//            profile.setUser(user);
//            profile.setName(companySignupRequest.getName());
//            if(!companySignupRequest.getDescription().isEmpty()){ profile.setDescription(companySignupRequest.getDescription());}
//            companyRepository.save(profile);
//
//            user.setCompanyProfile(profile);
//            userRepository.save(user);
//
//            log.info("Company Profile added: {}", profile.getName());
//            return new SubmitResponse(true, profile.getName());
//        } catch (Exception e) {
//            return new SubmitResponse(false, e.getLocalizedMessage());
//        }
//    }
}

