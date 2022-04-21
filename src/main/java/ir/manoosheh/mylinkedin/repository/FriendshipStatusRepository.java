package ir.manoosheh.mylinkedin.repository;

import ir.manoosheh.mylinkedin.model.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipStatusRepository extends JpaRepository<FriendshipStatus, Long> {
}