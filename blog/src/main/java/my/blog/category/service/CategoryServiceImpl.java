package my.blog.category.service;

import lombok.RequiredArgsConstructor;
import my.blog.board.domain.BoardRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

    @Override
    public Long saveCategory(String name) {
        Category findCategory = categoryRepository.findByCategoryName(name);
        if (findCategory != null) {
            throw new IllegalArgumentException("이미 존재하는 카테고리 입니다.");
        }
        Category category = Category.from(name); // 생성메서드
        Category saveCategory = categoryRepository.save(category);

        return saveCategory.getId();
    }

    @Override
    public void deleteCategory(String name) {
        // 해당 카테고리 아래에 글이 하나라도 있다면 삭제x
        Long boardCount = boardRepository.getBoardCountByCategory(name);
        if (boardCount == 0L) {
            throw new IllegalStateException("카테고리 아래에 글이 존재합니다.");
        }
        categoryRepository.deleteByCategoryName(name);
    }

    @Override
    public void updateCategory(String name) {
        Category category = categoryRepository.findByCategoryName(name);
        category.edit(name);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @PostConstruct
    private void insertDummyCategory() {
        Category category = categoryRepository.findByCategoryName("total");
        String dummyCategoryName = "total";
        if (category == null) {
            saveCategory(dummyCategoryName);
        }
    }
}
