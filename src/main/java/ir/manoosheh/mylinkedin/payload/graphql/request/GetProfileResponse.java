package ir.manoosheh.mylinkedin.payload.graphql.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProfileResponse {
    private String userId;
    private boolean isActive;
    private boolean isCompany;
    private String name;
    private String description;
}