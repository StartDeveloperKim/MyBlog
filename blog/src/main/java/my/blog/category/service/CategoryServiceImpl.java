package my.blog.category.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.BoardRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.category.dto.CategoryDto;
import my.blog.category.exception.DuplicateCategoryException;
import my.blog.category.exception.WritingExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

    @Override
    public void saveCategory(String name) {
        Boolean categoryCheck = categoryRepository.existsByCategoryName(name);
        if (categoryCheck) {
            throw new DuplicateCategoryException("이미 존재하는 카테고리 입니다.");
        }
        Category category = Category.from(name); // 생성메서드
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String name) {
        // 해당 카테고리 아래에 글이 하나라도 있다면 삭제x
        Long boardCount = boardRepository.getBoardCountByCategory(name);
        if (boardCount == 0L) {
            throw new WritingExistException("카테고리 아래에 글이 존재합니다.");
        }
        categoryRepository.deleteByCategoryName(name);
    }

    @Override
    public void updateCategory(String name) {
        Category category = categoryRepository.findByCategoryName(name);
        category.edit(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> getCategoryList() {
        List<Category> all = categoryRepository.findAll();
        return all.stream().map(CategoryDto::new)
                .collect(Collectors.toList());
    }

    /*더미 카테고리 생성 -> 글을 작성할 때 카테고리 설정을 하지 않으면 해당 카테고리에 들어간다.*/
    /*ddl-auto 옵션을 create 할 때만 사용하자*/
//    @PostConstruct
//    private void insertDummyCategory() {
//        Category category = categoryRepository.findByCategoryName("TOTAL");
//        String dummyCategoryName = "TOTAL";
//        if (category == null) {
//            saveCategory(dummyCategoryName);
//            //아래 세줄은 실험 코드
//            saveCategory("스프링");
//            saveCategory("JPA");
//            saveCategory("HTML/CSS");
//        }
//
//    }
}
