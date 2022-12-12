package my.blog.category.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.BoardRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.category.dto.*;
import my.blog.category.dto.request.CategoryAddDto;
import my.blog.category.dto.response.CategoryInfoDto;
import my.blog.category.dto.response.CategoryLayoutDto;
import my.blog.category.exception.DuplicateCategoryException;
import my.blog.category.exception.WritingExistException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

    @CacheEvict(value = "CategoryLayoutStore", allEntries = true)
    @Override
    public Long saveCategory(CategoryAddDto categoryAddDto) {
        //카테고리는 중복되면 안된다.
        boolean existCategory = checkDuplicateCategoryName(categoryAddDto);
        if (existCategory) {
            throw new DuplicateCategoryException("사용중인 카테고리 이름입니다.");
        }
        Category category = Category.from(categoryAddDto.getCategoryName(), categoryAddDto.getParentCategoryId()); // 생성메서드
        Category saveCategory = categoryRepository.save(category);

        return saveCategory.getId();
    }

    @CacheEvict(value = "CategoryLayoutStore", allEntries = true)
    @Override
    public void deleteCategory(Long id) {
        // 해당 카테고리 아래에 글이 하나라도 있다면 삭제x
        Long boardCount = boardRepository.getBoardCountByCategory(id);
        if (boardCount != 0L) {
            throw new WritingExistException("카테고리 아래에 글이 존재합니다.");
        }
        categoryRepository.deleteById(id); // 메서드이름 고치자
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
    @Cacheable("CategoryLayoutStore")
    @Override
    public Map<Long, CategoryLayoutDto> getCategoryList() {
        List<CategoryInfoDto> categoryDto = categoryRepository.getCategoryInfoDto();

        return HierarchicalCategory.from(categoryDto);
    }

    private boolean checkDuplicateCategoryName(CategoryAddDto categoryAddDto) {
        boolean existCategory;
        if (categoryAddDto.getParentCategoryId() == null) {
            existCategory = categoryRepository.existsByCategoryName(categoryAddDto.getCategoryName());
        } else {
            existCategory = categoryRepository.existByNameAndParentId(categoryAddDto.getParentCategoryId(),
                    categoryAddDto.getCategoryName());
        }
        return existCategory;
    }

    /*더미 카테고리 생성 -> 글을 작성할 때 카테고리 설정을 하지 않으면 해당 카테고리에 들어간다.*/
    /*ddl-auto 옵션을 create 할 때만 사용하자*/
//    @PostConstruct
//    private void insertDummyCategory() {
//        Category category = categoryRepository.findByCategoryName("total");
//        String dummyCategoryName = "total";
//        if (category == null) {
//            saveCategory(new CategoryAddDto(dummyCategoryName, null));
//            //아래 세줄은 실험 코드
//        }
//
//    }
}
