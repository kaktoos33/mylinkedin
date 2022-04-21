package ir.manoosheh.mylinkedin.service;

import ir.manoosheh.mylinkedin.model.Likes;
import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.model.UserPost;
import ir.manoosheh.mylinkedin.repository.LikesRepository;
import ir.manoosheh.mylinkedin.repository.UserPostRepository;
import ir.manoosheh.mylinkedin.repository.UserProfileRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class LikesService {
    @Autowired
    final LikesRepository likesRepository;
    @Autowired
    final UserService userService;
    final UserPostRepository userPostRepository;
    @Autowired
    final UserProfileRepository userProfileRepository;

    public boolean likePost(UserPost userPost) {

        Likes newLike = new Likes(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), userPost);
        likesRepository.save(newLike);
        return true;

    }

//    public boolean doLike(Long id) throws Exception{
//        final Long userId = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
//        final UserProfile profile = userProfileRepository.findUserProfileByUser((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//
//        Likes oldLike = likesRepository.findByUserProfileIdAndUserPostId(userId, id);
//
//        Optional<UserPost> oPost = userPostRepository.findById(id);
//        UserPost post = oPost.get();
//
//        try {
//            if (oldLike == null) { // 좋아요 안한 상태 (추가)
//                Likes newLike = Likes.builder().userPost(post).userProfile(profile).build();
//
//                likesRepository.save(newLike);
//                // 좋아요 카운트 증가(리턴 값 수정)
//            } else { // 좋아요 한 상태 (삭제)
//                likesRepository.delete(oldLike);
//                // 좋아요 카운트 증가(리턴 값 수정)
//            }
//            return true;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//
//    }
}

