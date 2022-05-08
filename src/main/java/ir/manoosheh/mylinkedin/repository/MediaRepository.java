package ir.manoosheh.mylinkedin.repository;

import ir.manoosheh.mylinkedin.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    Optional<Media> getByFilename(String filename);

}
