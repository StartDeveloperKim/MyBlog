package my.blog.tag.domain;

import lombok.Getter;

import javax.persistence.*;

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

}
