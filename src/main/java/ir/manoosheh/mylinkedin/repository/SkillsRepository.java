package ir.manoosheh.mylinkedin.repository;

import ir.manoosheh.mylinkedin.model.Skills;
import ir.manoosheh.mylinkedin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long> {

    Skills findByUserAndTagId(Long userId, Long tagId);

    List<Skills> getSkillsByUser(User user);

//    Set<Tag> getTagsByUser(User user);


}
