package my.blog.board.domain.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class FileStore {

    private final GitHubImgUploadService gitHubImgUploadService;

    public String storeFile(MultipartFile multipartFile, ImageType imageType) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        ImageUploadFile imageUploadFile = ImageUploadFile.of(originalFilename);

        return gitHubImgUploadService.uploadFile(multipartFile, imageUploadFile.getStoreFileName(), imageType); // 저장경로 반환
    }
}
