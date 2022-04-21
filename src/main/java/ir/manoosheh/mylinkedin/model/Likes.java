package ir.manoosheh.mylinkedin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
//    @JsonIgnoreProperties({"username","first_name","last_name"})
    private User user;

    @ManyToOne
    @JsonIgnoreProperties({"content", "createdDate"})
    private UserPost userPost;

    public Likes(User user, UserPost userPost) {
        this.user = user;
        this.userPost = userPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Likes likes = (Likes) o;
        return id != null && Objects.equals(id, likes.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
