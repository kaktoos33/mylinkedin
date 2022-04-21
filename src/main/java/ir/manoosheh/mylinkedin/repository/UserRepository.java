package ir.manoosheh.mylinkedin.repository;

import ir.manoosheh.mylinkedin.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByUsername(String email);


//    Boolean existsByUsername(String email);

//    User findByUsernameIs(String email);


    @NotNull
    Optional<User> findById(@NotNull Long id);
}