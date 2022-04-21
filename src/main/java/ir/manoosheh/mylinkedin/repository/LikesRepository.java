package ir.manoosheh.mylinkedin.repository;

import ir.manoosheh.mylinkedin.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Likes findByUserAndUserPostId(Long userId, Long postId);

    int countByUserPostId(Long id);


}
