package ir.manoosheh.mylinkedin.payload.graphql.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {
    private boolean success;
    private String message;
    private String email;
    private boolean isCompany;
}