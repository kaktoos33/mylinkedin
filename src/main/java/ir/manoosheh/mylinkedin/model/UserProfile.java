package ir.manoosheh.mylinkedin.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "user_profile",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"})
        }
)
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(name = "username",columnDefinition="VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_persian_ci")
    private String username;
    @Column(name = "first_name")//,columnDefinition="VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_persian_ci")
    private String firstName;
    @Column(name = "last_name")//,columnDefinition="VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_persian_ci")
    private String lastName;
    @Transient
    private String fullName;
//    @Column(name = "description",columnDefinition="VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_persian_ci")
    private String description;
    @Column(name = "last_title")//,columnDefinition="VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_persian_ci")
    private String title;
    @Column(name = "company_name")//,columnDefinition="VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_persian_ci")
    private String company;
    private String startedAtMonth;
    private String startedAtYear;
    private String finishedAtMonth;
    private String finishedAtYear;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", unique = true)
    private User user;

    @OneToMany(mappedBy = "userProfile")
    @ToString.Exclude
    private List<UserPost> userPosts = new ArrayList<>();

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

