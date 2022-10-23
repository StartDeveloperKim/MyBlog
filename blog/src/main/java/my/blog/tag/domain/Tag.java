package my.blog.tag.domain;

import lombok.Getter;
import my.blog.boardTag.domain.BoardTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SequenceGenerator(
        name = "TAG_SEQ_GENERATOR",
        sequenceName = "TAG_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_SEQ_GENERATOR")
    @Column(name = "tag_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String tagName;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private List<BoardTag> boardTags = new ArrayList<>();

    private Tag(String tagName) {
        this.tagName = tagName;
    }

    private Tag() {
    }

    public static Tag of(String tagName) {
        return new Tag(tagName);
    }
}
