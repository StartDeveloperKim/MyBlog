package my.blog.category.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.BoardRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.category.dto.CategoryAddDto;
import my.blog.category.dto.CategoryDto;
import my.blog.category.dto.CategoryRespInterface;
import my.blog.category.exception.DuplicateCategoryException;
import my.blog.category.exception.WritingExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

    @Override
    public Long saveCategory(CategoryAddDto categoryAddDto) {
        Boolean categoryCheck = categoryRepository.existsByCategoryName(categoryAddDto.getCategoryName());
        if (categoryCheck) {
            throw new DuplicateCategoryException("이미 존재하는 카테고리 입니다.");
            // 서브카테고리는 중복이 되도 될거같다. 음... 이 때 확인하는 로직을 만들어보자
        }
        Category category = Category.from(categoryAddDto.getCategoryName(), categoryAddDto.getParentCategoryId()); // 생성메서드
        Category saveCategory = categoryRepository.save(category);

        return saveCategory.getId();
    }

    @Override
    public void deleteCategory(Long id) {
        // 해당 카테고리 아래에 글이 하나라도 있다면 삭제x
        Long boardCount = boardRepository.getBoardCountByCategory(id);
        if (boardCount != 0L) {
            throw new WritingExistException("카테고리 아래에 글이 존재합니다.");
        }
        categoryRepository.deleteByCategoryName(id);
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
    public Map<Long, CategoryDto> getCategoryList() {
        Map<Long, CategoryDto> categoryResult = new HashMap<>();
        List<CategoryRespInterface> categoryDto = categoryRepository.findCategoryDto();

        for (CategoryRespInterface category : categoryDto) {
            System.out.println("category.getParentCategoryId() + category.getName() = " + category.getParentCategoryId() + category.getName());
            if (category.getParentCategoryId() == null) {
                categoryResult.put(category.getId(), new CategoryDto(
                        category.getId(), category.getName(), category.getCategoryNum()));
            } else {
                categoryResult.get(category.getParentCategoryId())
                        .getChildCategory()
                        .add(new CategoryDto(category.getId(), category.getName(), category.getCategoryNum()));
            }
        }

        return categoryResult;
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
