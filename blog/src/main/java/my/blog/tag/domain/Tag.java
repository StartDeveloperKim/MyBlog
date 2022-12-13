package my.blog.tag.domain;

import lombok.Getter;
import my.blog.boardTag.domain.BoardTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String tagName;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private List<BoardTag> boardTags = new ArrayList<>();

    protected Tag() {
    }


    public static Tag newInstance(String tagName) {
        Tag tag = new Tag();
        tag.tagName = tagName;
        return tag;
    }
}
