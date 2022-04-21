package ir.manoosheh.mylinkedin.payload.graphql.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostOut implements Comparable<UserPostOut> {
    private FriendsResponse postOwner;
    private String userPostId;
    private String content;
    private Date createdAt;

    @Override
    public int compareTo(@NotNull UserPostOut userPostOut) {
        return this.getCreatedAt().compareTo(userPostOut.getCreatedAt());
    }
//    private String file;

}