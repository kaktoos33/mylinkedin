package ir.manoosheh.mylinkedin.service;

import ir.manoosheh.mylinkedin.model.EFriendshipStatus;
import ir.manoosheh.mylinkedin.model.Friendship;
import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.payload.graphql.response.FriendsResponse;
import ir.manoosheh.mylinkedin.repository.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendshipService {
    final private FriendshipRepository friendshipRepository;
    final private UserService userService;

    public boolean isFriend(User friend) {
        User user = userService.getUser();
        boolean result = false;
        List<Friendship> friends = friendshipRepository.getFriendshipsByUserReceiverOrUserSender(user, user).get();
        boolean check = friends.contains(friend);
        log.info("friend check is " + check);
        long friedNumber = friends.stream()
                .filter(s -> (
                        s.getUserSender().getId() == friend.getId()
                                || s.getUserReceiver().getId() == friend.getId())).count();
        if (friedNumber > 0)
            result = true;
        return result;
    }

    public String getFriendshipStatus(User friend) {
        User user = userService.getUser();
        Optional<Friendship> friendship = friendshipRepository.
                getFriendshipByUserReceiverEqualsAndUserSenderEquals(user, friend);
        if (!friendship.isPresent())
            friendship = friendshipRepository.
                    getFriendshipByUserReceiverEqualsAndUserSenderEquals(friend, user);
        if (!friendship.isPresent())
            return "notFriend";
        else {
            switch (friendship.get().getStatus()) {
                case FRIENDS:
                    return "friend";
                case REQUEST_SENT:
                    if (user.equals(friendship.get().getUserSender()))
                        return "requestSent";
                    else if (user.equals(friendship.get().getUserReceiver()))
                        return "requestReceived";
            }
        }
        return "notFriend";
    }

    public List<FriendsResponse> getFriendsResponse() {
        List<User> friends = this.getFriendsList();
        List<FriendsResponse> result = friends.stream()
                .map(s -> new FriendsResponse(
                        s.getId().toString(),
                        s.getUserProfile().getFullName(),
                        s.getUserProfile().getDescription(),
                        this.getFriendshipStatus(s)
                ))
                .collect(Collectors.toList());

        return result;
    }

    public List<User> getFriendsList() {
        User user = userService.getUser();
        List<Friendship> friendships = friendshipRepository
                .getFriendshipsByUserReceiverOrUserSender(user, user).get()
                .stream().filter(s -> s.getStatus().equals(EFriendshipStatus.FRIENDS))
                .collect(Collectors.toList());
        return friendships.stream()
                .map(s -> s.getUserSender().equals(user) ? s.getUserReceiver() : s.getUserSender())
                .collect(Collectors.toList());

    }

    public List<User> getFriendsList(User user) {
        List<Friendship> friendships = friendshipRepository.getFriendshipsByUserReceiverOrUserSender(user, user).get();
        return friendships.stream()
                .filter(s -> s.getStatus().equals(EFriendshipStatus.FRIENDS))
                .map(s -> s.getUserSender().equals(user) ? s.getUserReceiver() : s.getUserSender())
                .collect(Collectors.toList());
    }

    public List<User> getNotFriendNotFriendOfFriendList() {
        List<User> friendFriendOfFriend = this.getFriendsList();
        friendFriendOfFriend.addAll(this.getFriendsOfFriendsList());

        return userService.getAllUser().stream()
                .filter(s -> !friendFriendOfFriend.contains(s) && !s.equals(userService.getUser()))
                .collect(Collectors.toList());
    }

    public List<FriendsResponse> getNotFriendNotFriendOfFriend() {
        return this.getNotFriendNotFriendOfFriendList().stream()
                .map(s -> new FriendsResponse(
                        s.getId().toString(),
                        s.getUserProfile().getFullName(),
                        s.getUserProfile().getDescription(),
                        this.getFriendshipStatus(s)))
                .collect(Collectors.toList());

    }

    public List<User> getFriendsOfFriendsList() {
        List<User> friendsOfFriends = new ArrayList<>();
        List<User> friends = this.getFriendsList();
        for (User friend : friends) {
            List<User> friendsOfFriend = this.getFriendsList(friend);
            friendsOfFriends.addAll(friendsOfFriend.stream()
                    .filter(s -> !friendsOfFriends.contains(s)).collect(Collectors.toList()));
        }
        return friendsOfFriends.stream()
                .filter(s -> !s.equals(userService.getUser()))
                .collect(Collectors.toList());
    }

    public List<FriendsResponse> getFriendsOfFriends() {
        List<User> friendsOfFriends = this.getFriendsOfFriendsList();

        return friendsOfFriends.stream()
                .map(s -> new FriendsResponse(
                        s.getId().toString(),
                        s.getUserProfile().getFullName(),
                        s.getUserProfile().getDescription(),
                        this.getFriendshipStatus(s)))
                .collect(Collectors.toList());
    }

    public List<FriendsResponse> getFriendSuggestions() {
        List<FriendsResponse> friendsSuggestion = this.getFriendsOfFriends();
        friendsSuggestion.addAll(this.getNotFriendNotFriendOfFriend());
        return friendsSuggestion.stream().filter(s -> s.getStatus() == "notFriend").collect(Collectors.toList());
    }

    public boolean sendFriendshipRequest(User userReceiver) {
        Friendship friendship =
                new Friendship(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()),
                        userReceiver, EFriendshipStatus.REQUEST_SENT);
        friendshipRepository.save(friendship);
        return true;
    }

    public FriendsResponse convertUserToFriendResponse(User user) {

        return new FriendsResponse(user.getId().toString(),
                user.getUserProfile().getFullName(),
                user.getUserProfile().getDescription(),
                "no");
    }

    public boolean acceptFriendshipRequest(User userSender) {
        User user = userService.getUser();
        Optional<Friendship> friendship = friendshipRepository.getFriendshipByUserReceiverEqualsAndUserSenderEquals(user, userSender);
        if (friendship.isPresent())
            if (friendship.get().getStatus() == EFriendshipStatus.REQUEST_SENT) {
                friendship.get().setStatus(EFriendshipStatus.FRIENDS);
                friendshipRepository.save(friendship.get());
            }

        return true;
    }

    public boolean denyFriendshipRequest(User userSender) {
        User user = userService.getUser();
        Optional<Friendship> friendship = friendshipRepository.getFriendshipByUserReceiverEqualsAndUserSenderEquals(user, userSender);
        if (friendship.isPresent())
            if (friendship.get().getStatus() == EFriendshipStatus.REQUEST_SENT) {
                friendship.get().setStatus(EFriendshipStatus.REQUEST_CANCELED);
                friendshipRepository.save(friendship.get());
            }

        return true;

    }

    public List<User> getMyExistingSuggestionsList() {
        User user = userService.getUser();

        List<Friendship> friendships = friendshipRepository
                .getFriendshipsByUserReceiverOrUserSender(
                        user, user).get()
                .stream()
                .filter(s -> s.getStatus().equals(EFriendshipStatus.REQUEST_SENT))
                .collect(Collectors.toList());

        return friendships.stream()
                .map(s -> s.getUserSender().equals(user) ? s.getUserReceiver() : s.getUserSender())
                .collect(Collectors.toList());

    }

    public List<FriendsResponse> getMyExistingSuggestions() {
        List<User> friends = this.getMyExistingSuggestionsList();
        List<FriendsResponse> result = friends.stream()
                .map(s -> new FriendsResponse(s.getId().toString(),
                        s.getUserProfile().getFullName(),
                        s.getUserProfile().getDescription(),
                        this.getFriendshipStatus(s)))
                .collect(Collectors.toList());
        return result;

    }
}

