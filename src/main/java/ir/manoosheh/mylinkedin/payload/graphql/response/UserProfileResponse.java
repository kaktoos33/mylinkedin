package ir.manoosheh.mylinkedin.payload.graphql.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private String firstName = "";
    private String lastName = "";
    private String username = "";
    private String description = "";
    private String company = "";
    private String startedAtMonth = "";
    private String startedAtYear = "";
    private String finishedAtMonth = "";
    private String finishedAtYear = "";
    private String title = "";
//    private List<UserPostOut> posts;
}

