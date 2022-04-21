package ir.manoosheh.mylinkedin.payload.graphql.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String userId;
    private Boolean isCompany;
    private String name;
    private String description;
    private Boolean isActive;
    public UserResponse(String userId,Boolean isCompany,Boolean isActive){
        this.userId = userId;
        this.isCompany = isCompany;
        this.isActive = isActive;
    }
}

