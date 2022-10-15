package my.blog.boardTag.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@SequenceGenerator(
        name = "BOARDTAG_SEQ_GENERATOR",
        sequenceName = "BOARDTAG_SEQ",
        initialValue = 1,
        allocationSize = 1)
public class BoardTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARDTAG_SEQ_GENERATOR")

}
