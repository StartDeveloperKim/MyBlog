package my.blog.board.domain.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class FileStore {

    /*2022-10-27 현재 로컬 저장결로로 설정했다. 나중에 다른 방법이 없는가 찾아보자*/
    /*2022-10-27 깃허브를 원격저장소로 사용한다.*/
    private final GitHubImgUploadService gitHubImgUploadService;

    public String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        ImageUploadFile imageUploadFile = ImageUploadFile.of(originalFilename);

        return gitHubImgUploadService.uploadFile(multipartFile, imageUploadFile.getStoreFileName()); // 저장경로 반환
    }
}
