package my.blog.tag.service;

import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.tag.domain.TagRepository;
import my.blog.tag.tool.ParsingTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TagServiceImpl implements TagService{

    private TagRepository tagRepository;
    private BoardTagRepository boardTagRepository;

    @Override
    public void saveTags(String tags) {
        List<String> tagList = new ArrayList<>();

        /*태그 파싱*/
        List<Map<String, String>> parsingFromTagList = ParsingTool.getGson().fromJson(tags, ArrayList.class);
        for (Map<String, String> parsingTag : parsingFromTagList){
            tagList.add(parsingTag.get("value"));
        }

        /*중복태그 확인*/
    }
}
