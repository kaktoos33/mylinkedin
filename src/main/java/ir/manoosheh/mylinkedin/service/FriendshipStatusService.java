package ir.manoosheh.mylinkedin.service;

import ir.manoosheh.mylinkedin.repository.FriendshipStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipStatusService {

    @Autowired
    final private FriendshipStatusRepository friendshipStatusRepository;

    public void addFriendshipStatus() {
//        FriendshipStatus REQUEST_SENT_Status = new FriendshipStatus();
//        REQUEST_SENT_Status.setStatus(EFriendshipStatus.REQUEST_SENT.toString());
//        friendshipStatusRepository.save(REQUEST_SENT_Status);
//
//        FriendshipStatus FRIENDS_Status = new FriendshipStatus();
//        FRIENDS_Status.setStatus(EFriendshipStatus.FRIENDS.toString());
//        friendshipStatusRepository.save(FRIENDS_Status);
//
//        FriendshipStatus REQUEST_CANCELED_Status = new FriendshipStatus();
//        REQUEST_CANCELED_Status.setStatus(EFriendshipStatus.REQUEST_CANCELED.toString());
//        friendshipStatusRepository.save(REQUEST_CANCELED_Status);


    }

}
