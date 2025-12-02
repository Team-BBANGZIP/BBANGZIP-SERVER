package org.sopt.user.service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryColor;
import org.sopt.category.facade.CategoryFacade;
import org.sopt.todo.facade.TodoFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserOnboardingService {

    private final CategoryFacade categoryFacade;
    private final TodoFacade todoFacade;

    @Transactional
    public void createDefaultsFor(long userId) {

        int currentCategoryCount = categoryFacade.getCategoryCountByUserId(userId);
        int categoryOrder = currentCategoryCount;

        List<DefaultCategory> defaults = List.of(
                new DefaultCategory(
                        "WELCOME",
                        CategoryColor.RED1,
                        List.of(
                                new DefaultTodo(
                                        "제 과제 빵점에 오신 걸 환영합니다!",
                                        LocalDate.now(),
                                        (LocalTime) null
                                )
                        )
                )
        );

        for (DefaultCategory defaultCategory : defaults) {

            Category category = Category.builder()
                    .userId(userId)
                    .name(defaultCategory.getName())
                    .color(defaultCategory.getColor())
                    .isStopped(false)
                    .order(categoryOrder++)
                    .build();

            Category saved = categoryFacade.saveCategory(category);

            int todoOrder = 0;
            for (DefaultTodo defaultTodo : defaultCategory.getTodos()) {
                todoFacade.saveTodo(
                        saved.getId(),
                        defaultTodo.getContent(),
                        defaultTodo.getTargetDate(),
                        defaultTodo.getStartTime(),
                        false,           // 기본 TODO는 항상 미완료
                        todoOrder++
                );
            }
        }
    }

    @Getter
    @AllArgsConstructor
    private static class DefaultCategory {
        private String name;
        private CategoryColor color;
        private List<DefaultTodo> todos;
    }

    @Getter
    @AllArgsConstructor
    private static class DefaultTodo {
        private String content;
        private LocalDate targetDate;
        private LocalTime startTime;
    }
}
