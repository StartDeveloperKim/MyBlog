package my.blog.heart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class HeartRequest {

    private Long userId;
    private Long boardId;
}
