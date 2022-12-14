package my.blog.heart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeartResponse {

    private Long heartCount;

    public HeartResponse(Long heartCount) {
        this.heartCount = heartCount;
    }
}
