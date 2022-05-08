package ir.manoosheh.mylinkedin.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "userpost")
public class UserPost implements Comparable<UserPost> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Column(name="content",columnDefinition="ٰVARCHAR(255) CHARACTER SET utf8 COLLATE utf8_persian_ci")
    private String content;

    @OneToOne
    @JoinColumn(name = "media_id", referencedColumnName = "id", unique = true)
    private Media media;

    private String file;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToOne
//    @JsonIgnoreProperties({"username", "first_name", "last_name"})
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Likes> likes = new ArrayList<>();


    @Override
    public int compareTo(@NotNull UserPost userPost) {
        return this.getCreatedDate().compareTo(userPost.getCreatedDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserPost userPost = (UserPost) o;
        return id != null && Objects.equals(id, userPost.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
