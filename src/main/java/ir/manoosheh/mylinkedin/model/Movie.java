package ir.manoosheh.mylinkedin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Movie {

    @Id
    private Integer id;
    private String name;
    private String director;
}
