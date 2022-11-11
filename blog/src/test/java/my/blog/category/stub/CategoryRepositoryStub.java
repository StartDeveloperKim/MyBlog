package my.blog.category.stub;

import my.blog.category.domain.Category;

public class CategoryRepositoryStub {

    private final Category category = Category.from("스프링", null);

    public Long saveParentCategory(Category category) {
        return 1L;
    }

    public Long saveChildCategory(Category category) {
        return 2L;
    }
}
