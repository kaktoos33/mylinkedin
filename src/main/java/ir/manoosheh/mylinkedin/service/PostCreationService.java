package ir.manoosheh.mylinkedin.service;

import ir.manoosheh.mylinkedin.model.Media;
import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.model.UserPost;
import ir.manoosheh.mylinkedin.payload.graphql.request.NewUserPostRequest;
import ir.manoosheh.mylinkedin.payload.graphql.response.SubmitResponse;
import ir.manoosheh.mylinkedin.repository.MediaRepository;
import ir.manoosheh.mylinkedin.repository.UserPostRepository;
import ir.manoosheh.mylinkedin.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;

@Service
@Data
@RequiredArgsConstructor
public class PostCreationService {

    @Autowired
    final UserRepository userRepository;
    @Autowired
    final private UserProfileService userProfileService;
    @Autowired
    final UserPostRepository userPostRepository;
    @Autowired
    final private MediaRepository mediaRepository;
    @Autowired
    final UserService userService;
//    final UploadService uploadService;
//    final CompanyRepository companyRepository;

    public UserPost getUserPostById(Long postId) {
        return userPostRepository.findById(postId).get();//.orElseThrow();

    }

    @Transactional
    public SubmitResponse newUserPost(NewUserPostRequest newUserPostRequest) {

        User user = userService.getUser();

        UserPost post = new UserPost();
        post.setContent(newUserPostRequest.getContent());
        post.setCreatedDate(new Date());
        Media newMedia = new Media(newUserPostRequest.getMedia().getFileName()
                , Long.valueOf(newUserPostRequest.getMedia().getSize())
                , newUserPostRequest.getMedia().getType());
        newMedia.setUserPost(post);
        mediaRepository.save(newMedia);
        post.setMedia(newMedia);
        post.setUserProfile(user.getUserProfile());

        userPostRepository.save(post);


        return new SubmitResponse(true, "Successfully submitted");

    }


    public SubmitResponse newUserPost(NewUserPostRequest newUserPostRequest, MultipartFile file) {
        String email = userService.getUsername();
        if (Objects.equals(email, "anonymousUser")) {
            return new SubmitResponse(false, "User hasn't logged in yet");
        }
        try {
            UserPost post = new UserPost();
            post.setContent(newUserPostRequest.getContent());
            post.setCreatedDate(new Date());
            post.setUserProfile(userRepository.findByEmail(email).get().getUserProfile());
            if (!file.isEmpty()) {
//                post.setFile(uploadService.upload(file));
            }
            userPostRepository.save(post);
            return new SubmitResponse(true, "Successfully submitted");
        } catch (Exception e) {
            return new SubmitResponse(false, e.getLocalizedMessage());
        }
    }

//    public SubmitResponse newCompanyPost(NewCompanyPostRequest newCompanyPostRequest) {
//        String email = userService.getUsername();
//        if (Objects.equals(email, "anonymousUser")) {
//            return new SubmitResponse(false, "User hasn't logged in yet");
//        }
//        try {
//            CompanyProfile companyProfile = userRepository.findByUsernameIs(email).getCompanyProfile();
//            CompanyPost post = new CompanyPost();
//            post.setContent(newCompanyPostRequest.getContent());
//            post.setCreatedDate(new Date());
//            post.setCompanyProfile(companyProfile);
//
//            List<Tags> tempTags = new ArrayList<>();
//            for (String tagName : newCompanyPostRequest.getTags()) {
//                Tags tag = new Tags();
//                tag.setName(tagName);
//                tag.addCompanyProfile(companyProfile);
//                tempTags.add(tag);
//            }
//            post.setTagsCollection(tempTags);
//            companyPostRepository.save(post);
//            for (Tags tag : tempTags) {
//                tag.addCompanyPost(post);
//                Optional<Tags> existingTag = tagsRepository.findTagsByName(tag.getName());
//                if (existingTag.isPresent()) {
//                    Tags finalTag = existingTag.get();
//                    finalTag.addCompanyProfile(companyProfile);
//                    finalTag.addCompanyPost(post);
//                    tagsRepository.save(finalTag);
//                } else tagsRepository.save(tag);
//            }
//
////            tagRepository.saveAll(tempTags);
//            return new SubmitResponse(true, "Successfully submitted");
//
//        } catch (Exception e) {
//            return new SubmitResponse(false, e.getLocalizedMessage());
//        }
//    }
}

