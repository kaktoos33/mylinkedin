package ir.manoosheh.mylinkedin.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.model.UserPost;
import ir.manoosheh.mylinkedin.model.UserProfile;
import ir.manoosheh.mylinkedin.payload.graphql.request.GetProfileResponse;
import ir.manoosheh.mylinkedin.payload.graphql.response.FriendsResponse;
import ir.manoosheh.mylinkedin.payload.graphql.response.TagOut;
import ir.manoosheh.mylinkedin.payload.graphql.response.UserPostOut;
import ir.manoosheh.mylinkedin.payload.graphql.response.UserProfileResponse;
import ir.manoosheh.mylinkedin.repository.UserPostRepository;
import ir.manoosheh.mylinkedin.repository.UserProfileRepository;
import ir.manoosheh.mylinkedin.repository.UserRepository;
import ir.manoosheh.mylinkedin.service.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@Component
public class QueryResolver implements GraphQLQueryResolver {
    @Autowired
    final UserPostRepository userPostRepository;
    @Autowired
    final private UserService userService;
    @Autowired
    final UserProfileRepository userProfileRepository;
    @Autowired
    final UserRepository userRepository;

    @Autowired
    final private SkillsService skillsService;
    @Autowired
    final private TagService tagService;
    @Autowired
    final private FriendshipService friendshipService;
    @Autowired
    final private UserPostService userPostService;

    @PreAuthorize("isAuthenticated()")
    public String helloWorld(String name) {
        return "Hello " + name;
    }

    @PreAuthorize("isAuthenticated()")
    public List<UserPostOut> getUserPost() {
        return userPostService.getUserPost();
    }

    @PreAuthorize(("isAuthenticated()"))
    public List<FriendsResponse> getFriends() {
        return friendshipService.getFriendsResponse();
    }

    @PreAuthorize("isAuthenticated()")
    public List<FriendsResponse> getFriendsSuggestion() {
        return friendshipService.getFriendSuggestions();
    }

    @PreAuthorize("isAuthenticated()")
    public List<FriendsResponse> getMyExistingSuggestions() {
        return friendshipService.getMyExistingSuggestions();
    }

    @PreAuthorize("isAuthenticated()")
    public GetProfileResponse getProfile() {
        User user = userService.getUser();
        GetProfileResponse profileResponse = new GetProfileResponse();

        profileResponse.setUserId(String.valueOf(user.getId()));
        profileResponse.setActive(user.isEnabled());
        profileResponse.setCompany(user.getIsCompany());
        if (!user.getIsCompany()) {
            profileResponse.setName(user.getUserProfile().getFullName());
            profileResponse.setDescription(user.getUserProfile().getDescription());
        } else {
//            profileResponse.setName(user.getCompanyProfile().getName());
//            profileResponse.setDescription(user.getCompanyProfile().getDescription());
        }

        return profileResponse;
    }

    @PreAuthorize("isAuthenticated()")
    public List<TagOut> getSkills() {
//        List<String> skills = skillsService.getSkills();
        List<TagOut> skills = skillsService.getSkills().stream()
                .map(s -> new TagOut(s)).collect(Collectors.toList());
        return skills;
    }

    @PreAuthorize("isAuthenticated()")
    public List<TagOut> getTags() {
        List<TagOut> tags = tagService.getTags().stream()
                .map(s -> new TagOut(s.getName())).collect(Collectors.toList());
        return tags;
    }

    @PreAuthorize("isAuthenticated()")
    public UserProfileResponse getUserProfile() {
        User user = userService.getUser();
        UserProfile userProfile = user.getUserProfile();
        UserProfileResponse profile = new UserProfileResponse();
        profile.setFirstName(userProfile.getFirstName());
        profile.setLastName(userProfile.getLastName());
        profile.setId(String.valueOf(userProfile.getId()));
        profile.setUsername(userProfile.getUsername());
        profile.setDescription(userProfile.getDescription());
        return profile;
    }

    @PreAuthorize("isAuthenticated()")
    public UserProfileResponse userProfile(Long id) {
        try {
            UserProfile userProfile = userRepository.findById(id).get().getUserProfile();
            UserProfileResponse profile = new UserProfileResponse();
            profile.setFirstName(userProfile.getFirstName());
            profile.setLastName(userProfile.getLastName());
            profile.setId(String.valueOf(userProfile.getId()));
            profile.setUsername(userProfile.getUsername());
            profile.setDescription(userProfile.getDescription());
            List<UserPost> posts = userPostRepository.findUserPostsByUserProfileId(userProfile.getId());
            List<UserPostOut> userPostOuts = new ArrayList<>();


            for (UserPost item : posts) {
                UserPostOut post = new UserPostOut();
                post.setContent(item.getContent());
                post.setCreatedAt(item.getCreatedDate());
//                post.setFile(item.getFile());
                userPostOuts.add(post);
            }

//            profile.setPosts(userPostOuts);
            return profile;
        } catch (Exception e) {
//            return new UserProfileResponse(null, e.getMessage(), null, null, null, null);
            return new UserProfileResponse(null, e.getMessage(), null, null, null);
        }
    }

//    @PreAuthorize("isAuthenticated()")
//    public CompanyProfileResponse companyProfile(Long id) {
//        try {
//            CompanyProfile companyProfile = companyRepository.findById(id).get();
//            CompanyProfileResponse profile = new CompanyProfileResponse();
//            profile.setName(companyProfile.getName());
//            profile.setDescription(companyProfile.getDescription());
//            profile.setId(companyProfile.getId());
//            List<CompanyPost> posts = companyPostRepository.findCompanyPostsByCompanyProfileId(companyProfile.getId());
//            List<CompanyPostOut> companyPostOuts = new ArrayList<>();
//
//
//            for (CompanyPost companyPost : posts) {
//
//                CompanyPostOut companyPostOut = new CompanyPostOut();
//                companyPostOut.setContent(companyPost.getContent());
//
//                List<Tags> postTags = companyPost.getTagsCollection();
//                List<TagOut> tagOuts = new ArrayList<>();
//
//                for (Tags companyPostTag : postTags) {
//                    TagOut tagOut = new TagOut();
//                    tagOut.setName(companyPostTag.getName());
//                    if (!tagOuts.contains(tagOut))
//                        tagOuts.add(tagOut);
//                }
//                companyPostOut.setTags(tagOuts);
//                companyPostOuts.add(companyPostOut);
//            }
//
//            List<TagOut> profileTagsOut = new ArrayList<>();
//            List<Tags> profileTags = tagsRepository.findTagsByCompanyProfileId(profile.getId());
//
//            for (Tags profileTag : profileTags) {
//                TagOut tagOut = new TagOut(profileTag.getName());
//                profileTagsOut.add(tagOut);
//            }
//
//            profile.setTags(profileTagsOut);
//            profile.setPosts(companyPostOuts);
//            return profile;
//
//
//        } catch (Exception e) {
//            return new CompanyProfileResponse(null, e.getMessage(), null, null, null);
//
//        }
//    }

}
