package my.blog.category.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.BoardRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.category.dto.*;
import my.blog.category.dto.request.CategoryAddDto;
import my.blog.category.dto.response.CategoryEditDto;
import my.blog.category.dto.response.CategoryInfoDto;
import my.blog.category.dto.response.CategoryLayoutDto;
import my.blog.category.exception.DuplicateCategoryException;
import my.blog.category.exception.WritingExistException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

    @CacheEvict(value = "CategoryLayoutStore", allEntries = true)
    @Override
    public Long saveCategory(final Category category) {
        if (checkDuplicateCategoryName(category)) {
            throw new DuplicateCategoryException("사용중인 카테고리 이름입니다.");
        }

        return categoryRepository.save(category).getId();
    }

    private boolean checkDuplicateCategoryName(Category category) {
        if (category.getParentCategoryId() == null) {
            return categoryRepository.existsByCategoryName(category.getCategoryName());
        } else {
            return categoryRepository.existByNameAndParentId(category.getParentCategoryId(),
                    category.getCategoryName());
        }
    }

    @Override
    public List<CategoryEditDto> getAllCategory() {
        List<Category> all = categoryRepository.findAll();
        return all.stream()
                .map(category -> new CategoryEditDto(category.getId(), category.getCategoryName()))
                .collect(Collectors.toList());
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
