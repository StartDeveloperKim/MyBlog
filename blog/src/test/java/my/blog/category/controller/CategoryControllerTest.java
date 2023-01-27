package my.blog.category.controller;

import my.blog.auth.jwt.TokenProvider;
import my.blog.category.domain.Category;
import my.blog.category.dto.response.CategoryLayoutDto;
import my.blog.category.service.CategoryService;
import my.blog.category.service.CategoryServiceImpl;
import my.blog.config.JpaAuditingConfiguration;
import my.blog.factory.EntityFactory;
import ognl.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static my.blog.factory.EntityFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@Import({TokenProvider.class})
@MockBean(JpaAuditingConfiguration.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryServiceImpl categoryService;


    @BeforeEach
    void setUp() {
        Category parent = newCategoryInstance("Parent", 1L, null);
        Category child = newCategoryInstance("Child", 2L, 1L);

        Map<Long, CategoryLayoutDto> categoryLayout = new HashMap<>();
        categoryLayout.put(parent.getId(), new CategoryLayoutDto(parent.getId(), parent.getCategoryName(), 0L));
        categoryLayout.get(parent.getId()).getChildCategory()
                .add(new CategoryLayoutDto(child.getId(), child.getCategoryName(), 0L));

        given(categoryService.getCategoryList()).willReturn(categoryLayout);
    }

    @Test
    void 모든_카테고리를_조회한다() throws Exception {
        mockMvc.perform(get("/category"))
                .andExpect(status().isOk());
    }
}