package ir.manoosheh.mylinkedin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public Boolean getEmailVerified() {
//        return emailVerified;
//    }
//
//    public void setEmailVerified(Boolean emailVerified) {
//        this.emailVerified = emailVerified;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public AuthProvider getProvider() {
//        return provider;
//    }
//
//    public void setProvider(AuthProvider provider) {
//        this.provider = provider;
//    }
//
//    public String getProviderId() {
//        return providerId;
//    }
//
//    public void setProviderId(String providerId) {
//        this.providerId = providerId;
//    }


    //@Column(name = "email", unique = true, columnDefinition="VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_persian_ci")
    private String username;

    @Column(name = "is_active")
    private boolean enabled;

    @Column(name = "is_company")
    private Boolean isCompany;


    @Column(name = "avatar")
    private String avatar;

    @OneToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id", unique = true)
    private UserProfile userProfile;

//    @OneToOne
//    @JoinColumn(name = "company_profile_id", referencedColumnName = "id", unique = true)
//    private CompanyProfile companyProfile;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}