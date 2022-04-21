package ir.manoosheh.mylinkedin.payload.graphql.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagInput {
    private String name;
}
