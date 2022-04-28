package ir.manoosheh.mylinkedin.service;

import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.model.UserPost;
import ir.manoosheh.mylinkedin.model.UserProfile;
import ir.manoosheh.mylinkedin.payload.graphql.response.UserPostOut;
import ir.manoosheh.mylinkedin.repository.UserPostRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class UserPostService {

    @Autowired
    final private UserPostRepository userPostRepository;
    @Autowired
    final private FriendshipService friendshipService;
    @Autowired
    final private UserService userService;

    public List<UserPostOut> getUserPost() {
        User user = userService.getUser();
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfiles.add(user.getUserProfile());
        userProfiles.addAll(friendshipService.getFriendsList().stream().map(s -> s.getUserProfile()).collect(Collectors.toList()));
        List<UserPost> userPosts = new ArrayList<>();// userPostRepository.getUserPostsByUserProfileExists(userProfiles);
        for (UserProfile userProfile : userProfiles) {
            userPosts.addAll(userPostRepository.getUserPostsByUserProfile(userProfile));
        }
        Comparator<UserPostOut> comparatorUserPostOnDate =
                Comparator.naturalOrder();
        return userPosts.stream()
                .map(s -> new UserPostOut(
                        friendshipService
                                .convertUserToFriendResponse(s.getUserProfile().getUser()),
                        s.getId().toString(),
                        s.getContent(),
                        s.getMedia(),
                        s.getCreatedDate()
                ))
                .sorted(comparatorUserPostOnDate.reversed())
                .collect(Collectors.toList());
//        return null;
    }
}