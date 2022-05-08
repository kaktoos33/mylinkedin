package ir.manoosheh.mylinkedin.payload.graphql.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaInput {
    private String fileName;
    private String type;
    private String size;
}

