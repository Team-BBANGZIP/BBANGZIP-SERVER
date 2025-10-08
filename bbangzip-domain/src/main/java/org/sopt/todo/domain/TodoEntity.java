package org.sopt.todo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.common.BaseTimeEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.sopt.todo.domain.TodoTableConstants.COLUMN_CATEGORY_ID;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_CONTENT;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_ID;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_IS_COMPLETED;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_ORDER;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_START_TIME;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_TARGET_DATE;
import static org.sopt.todo.domain.TodoTableConstants.TABLE_TODO;

@Entity
@Getter
@Table(name = TABLE_TODO)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_CATEGORY_ID, nullable = false)
    private CategoryEntity category;

    @Column(name = COLUMN_CONTENT, nullable = false)
    private String content;

    @Column(name = COLUMN_START_TIME)
    private LocalTime startTime;

    @Column(name = COLUMN_IS_COMPLETED, nullable = false)
    private Boolean isCompleted;

    @Column(name = COLUMN_TARGET_DATE, nullable = false)
    private LocalDate targetDate;

    @Column(name = COLUMN_ORDER, nullable = false)
    private int order;

    public static TodoEntity forCreate(
            String content,
            CategoryEntity category,
            LocalDate targetDate,
            LocalTime startTime,
            boolean isCompleted,
            int order
    ) {
        TodoEntity entity = new TodoEntity();
        entity.content = content;
        entity.category = category;
        entity.targetDate = targetDate;
        entity.startTime = startTime;
        entity.isCompleted = isCompleted;
        entity.order = order;
        return entity;
    }

    public Todo toDomain() {
        return Todo.builder()
                .id(id)
                .category(category.toDomain())
                .content(content)
                .startTime(startTime)
                .isCompleted(isCompleted)
                .targetDate(targetDate)
                .order(order)
                .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateCompletion(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void updateStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * 다른날 또하기용 팩토리 메서드
     * - 내용, 카테고리, 시작시간은 그대로
     * - 완료 여부는 항상 false로 초기화
     * - 날짜만 새로운 targetDate로 교체
     */
    public static TodoEntity forRepeat(TodoEntity original, LocalDate newDate, int order) {
        return forCreate(
                original.getContent(),
                original.getCategory(),
                newDate,
                original.getStartTime(),
                false, // 항상 미완료로 생성
                order
        );
    }

    public void updateCategory(CategoryEntity newCategory) {
        this.category = newCategory;
    }
}
