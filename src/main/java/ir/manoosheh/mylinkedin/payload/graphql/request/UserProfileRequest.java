package ir.manoosheh.mylinkedin.payload.graphql.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String description;
    private String company;
    private String startedAtMonth;
    private String startedAtYear;
    private String finishedAtMonth;
    private String finishedAtYear;
    private String title;

}
