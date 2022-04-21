package ir.manoosheh.mylinkedin.repository;

import ir.manoosheh.mylinkedin.model.Friendship;
import ir.manoosheh.mylinkedin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Optional<List<Friendship>> getFriendshipByUserSender(User userSender);

    Optional<Friendship> getFriendshipByUserReceiverEqualsAndUserSenderEquals(User user1, User user2);

    Optional<List<Friendship>> getFriendshipsByUserReceiverOrUserSender(User user, User user2);

}
