package ir.manoosheh.mylinkedin.repository;

import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
//    @NotNull
//    @Override
//    Optional<UserProfile> findById(Long id);

    //    UserProfile findByUser(User user);
    boolean existsByUser(User user);

    boolean existsByUsername(String username);

    Optional<UserProfile> findUserProfileByUser(User user);
}

