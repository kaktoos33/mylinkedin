package ir.manoosheh.mylinkedin.repository;

import ir.manoosheh.mylinkedin.model.UserPost;
import ir.manoosheh.mylinkedin.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPostRepository extends JpaRepository<UserPost, Long> {
    List<UserPost> findUserPostsByUserProfileId(Long id);

    List<UserPost> getUserPostsByUserProfile(UserProfile userProfiles);


}

