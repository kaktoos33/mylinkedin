package ir.manoosheh.mylinkedin.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "friendship")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private User userSender;

    @ManyToOne
    private User userReceiver;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EFriendshipStatus status;

    public Friendship(User userSender, User userReceiver, EFriendshipStatus status) {
        this.userSender = userSender;
        this.userReceiver = userReceiver;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Friendship that = (Friendship) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

