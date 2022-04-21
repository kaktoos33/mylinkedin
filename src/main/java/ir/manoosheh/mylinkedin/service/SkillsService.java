package ir.manoosheh.mylinkedin.service;

import ir.manoosheh.mylinkedin.model.Skills;
import ir.manoosheh.mylinkedin.model.Tag;
import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.repository.SkillsRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@Service
@RequiredArgsConstructor
public class SkillsService {

    @Autowired
    final private UserService userService;
    @Autowired
    final private SkillsRepository skillsRepository;
    @Autowired
    final private TagService tagService;

    public boolean addSkills(List<Tag> tags) {
        User user = userService.getUser();
//        Set<Tag> userTags = skillsRepository.getTagsByUser(user);
//        for (Tag tag : tags) {
//            if (!userTags.contains(tag)) {
//                Skills skill = new Skills();
//                skill.setUser(user);
//                skill.setTag(tag);
//                skillsRepository.save(skill);
//            }
//
//        }
        log.info(tags.toString() + "query add skill");
        tagService.addTags(tags.stream().map(s -> s.getName()).collect(Collectors.toList()));
        List<Skills> skills = skillsRepository.getSkillsByUser(user);
        List<String> skillsStringList = skills.stream().map(s -> s.getTag().getName()).collect(Collectors.toList());
        List<Tag> filteredTags = tags.stream().filter(s -> !skillsStringList.contains(s.getName())).collect(Collectors.toList());
        for (Tag tag : filteredTags) {
            Skills skill = new Skills();
            skill.setUser(user);
            skill.setTag(tag);
            skillsRepository.save(skill);
        }

        return true;
    }

    public List<String> getSkills() {
        User user = userService.getUser();
        List<Skills> skills = skillsRepository.getSkillsByUser(user);
//        List<String> skillsList = new ArrayList<>();
//        for (Skills skill : skills) {
//            skillsList.add(skill.getTag().getName());
//        }
        return skills.stream().map(s -> s.getTag().getName()).collect(Collectors.toList());
//        return skillsList;
    }
}

