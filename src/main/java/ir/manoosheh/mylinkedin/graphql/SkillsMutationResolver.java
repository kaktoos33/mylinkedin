package ir.manoosheh.mylinkedin.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import ir.manoosheh.mylinkedin.model.Tag;
import ir.manoosheh.mylinkedin.service.SkillsService;
import ir.manoosheh.mylinkedin.service.TagService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class SkillsMutationResolver implements GraphQLMutationResolver {

    @Autowired
    final private TagService tagService;
    @Autowired
    final private SkillsService skillsService;

    @PreAuthorize("isAuthenticated()")
    public boolean addTags(List<String> tags) throws Exception {
        tagService.addTags(tags);
        return true;
    }

    @PreAuthorize("isAuthenticated()")
    public boolean addSkills(List<String> tagsName) throws Exception {

        tagService.addTags(tagsName);
        List<Tag> tags = tagService.getTagsByName(tagsName);
        skillsService.addSkills(tags);
        return true;
    }
}