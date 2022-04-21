package ir.manoosheh.mylinkedin.payload.graphql.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendsResponse {
    private String userId;
    private String name;
    private String description;
    private String status;
}
