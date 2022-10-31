package my.blog.tag.service;

import java.util.List;

public interface TagService {

    /*
    * TagService를 통해 Tag를 저장하는 과정이 필요할까??
    * 1. 중복확인을 하지않고 태그는 모두 저장한다 -> TagId를 통해 구별이 가능하지만 중복되는 값이 많아지면 DB에 불필요한 자료가 많아진다.
    * 2. 중복확인을 해버리면 그에따른 중복확인 쿼리가 계속나간다. 따라서 게시글을 저장하는데 있어 태그가 10개라면
    *    중복확인 쿼리가 10번나가고 10개가 중복되지 않았다면 10번의 INSERT 쿼리가 날라간다. 흠....
    * 3. Spring Data JPA의 saveAll() 사용 -> 벌크 쿼리로 날려준다.
    * */
    List<String> saveTags(String tags);

    Long findTagIdByTagName(String tagName);

}
