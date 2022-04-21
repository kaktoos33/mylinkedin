package ir.manoosheh.mylinkedin.service;

import ir.manoosheh.mylinkedin.model.Tag;
import ir.manoosheh.mylinkedin.repository.TagRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@RequiredArgsConstructor
public class TagService {
    @Autowired
    final private TagRepository tagRepository;

    public boolean addTags(List<String> tagsStrings) {
        List<String> tags = tagRepository.findAll().stream().map(s -> s.getName()).collect(Collectors.toList());
        List<String> newTags = tagsStrings.stream().filter(s -> !tags.contains(s)).collect(Collectors.toList());
        for (String tagString : newTags) {
            Tag tag = new Tag();
            tag.setName(tagString);
            tagRepository.save(tag);
        }

        return true;
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getTagsByName(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            Tag newTag = tagRepository.getByName(tagName).get();//.orElseThrow();
            if (newTag != null)
                tags.add(newTag);
        }
        return tags;
    }
}
