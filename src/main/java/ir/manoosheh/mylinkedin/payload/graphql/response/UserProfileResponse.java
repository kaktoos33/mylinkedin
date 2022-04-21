package ir.manoosheh.mylinkedin.payload.graphql.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String description;
//    private List<UserPostOut> posts;
}

