package ir.manoosheh.mylinkedin.repository;

import ir.manoosheh.mylinkedin.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> getByName(String name);
}

