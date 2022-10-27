package my.blog.board.domain.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@AllArgsConstructor
public class ImageUploadFile {

    private String uploadFileName; // 사용자가 입력한 파일이름 -> 사용자에게 보여주기 위함
    private String storeFileName; // 저장 파일이름 -> 중복방지와 구별을 위한 파일이름

    private ImageUploadFile(String uploadFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = createStoreFileName(uploadFileName);
    }

    private String createStoreFileName(String uploadFileName) {
        String ext = extractExt(uploadFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String uploadFileName) {
        int pos = uploadFileName.lastIndexOf("."); // 확장자 추출
        return uploadFileName.substring(pos + 1);
    }
    
    // 생성메서드
    public static ImageUploadFile of(String uploadFileName) {
        return new ImageUploadFile(uploadFileName);
    }
}
