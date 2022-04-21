package ir.manoosheh.mylinkedin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name="name", columnDefinition="VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_persian_ci")
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Skills> skills = new ArrayList<>();
}

