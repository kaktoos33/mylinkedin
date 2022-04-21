package ir.manoosheh.mylinkedin.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Tag tag;

    public Skills(User user, Tag tag) {
        this.user = user;
        this.tag = tag;
    }

}
