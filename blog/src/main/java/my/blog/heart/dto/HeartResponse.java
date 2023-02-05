package my.blog.heart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeartResponse {

    private boolean isHeartBoard; // 사용자가 해당 글에 좋아요를 눌렀나 안눌렀나 여부를 전송하자

    public HeartResponse(boolean isHeartBoard) {
        this.isHeartBoard = isHeartBoard;
    }
}
