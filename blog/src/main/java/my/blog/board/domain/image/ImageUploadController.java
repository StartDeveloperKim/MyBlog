package my.blog.board.domain.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class ImageUploadController {

    private final FileStore fileStore;

    @PostMapping("/thumbnail")
    public ResponseEntity<String> thumbnailUpload(@ModelAttribute MultipartFile img) throws IOException {
        /*깃허브 저장 서비스계층이 완성되면 거기서 반환되는 URL을 body에 담아서 반환하자*/
        String storeURL = fileStore.storeFile(img);
        log.info("썸네일 저장경로 {}", storeURL);

        return ResponseEntity.ok().body(storeURL); // 저장경로 반환
    }
}
