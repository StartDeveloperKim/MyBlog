package my.blog.board.domain.image;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@PropertySource(value = {"classpath:application-github.properties"})
@Service
public class GitHubImgUploadService {

    @Value("${git.token}")
    private String gitToken;

    @Value("${git.repository}")
    private String repositoryURL;

    @Value("${git.rootURL}")
    private String rootURL;

//    https://raw.githubusercontent.com/StartDeveloperKim/blogImg/main/Img/%EC%9D%BC%EC%83%81.png

    public String uploadFile(MultipartFile multipartFile, String storeFileName, ImageType imageType) throws IOException {
        GitHub gitHub = new GitHubBuilder().withOAuthToken(gitToken).build();
        GHRepository repository = gitHub.getRepository(repositoryURL);
        String imgFolder = imageType.name().equals("THUMBNAIL") ? "Img/" : "Img/BoardImageRepository/";
        String commitMessage = imageType.name().equals("THUMBNAIL") ? "thumbnail" : "boardEditorImage";

        repository.createContent().path(imgFolder + storeFileName)
                .content(multipartFile.getBytes()).message(commitMessage).branch("main").commit();
        return rootURL + repositoryURL + "/main/" + imgFolder + storeFileName + "?raw=true";
    }
}
