package ir.manoosheh.mylinkedin.payload.graphql.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String userId;
    private boolean isCompany;
    private String name;
    private String description;
    private boolean isActive;

    public UserResponse(String userId, Boolean isCompany, Boolean isActive) {
        this.userId = userId;
        this.isCompany = isCompany;
        this.isActive = isActive;
    }
}

