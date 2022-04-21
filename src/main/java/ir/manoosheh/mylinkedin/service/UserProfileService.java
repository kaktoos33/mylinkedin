package ir.manoosheh.mylinkedin.service;

import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.model.UserProfile;
import ir.manoosheh.mylinkedin.repository.UserProfileRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileService {
    @Autowired
    final private UserProfileRepository userProfileRepository;
    @Autowired
    final private UserService userService;


    public boolean addUserProfile(UserProfile userProfile) {
        User user = userService.getUser();

        userProfile.setUser(user);

        user.setUserProfile(userProfile);
        userProfileRepository.save(userProfile);
        userService.save(user);

        return true;
    }

}

