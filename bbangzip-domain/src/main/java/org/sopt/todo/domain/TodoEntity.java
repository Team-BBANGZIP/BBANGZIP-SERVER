package org.sopt.todo.domain;

import static org.sopt.todo.domain.TodoTableConstants.COLUMN_CATEGORY_ID;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_CONTENT;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_ID;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_IS_COMPLETED;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_ORDER;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_START_TIME;
import static org.sopt.todo.domain.TodoTableConstants.COLUMN_TARGET_DATE;
import static org.sopt.todo.domain.TodoTableConstants.TABLE_TODO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.common.BaseTimeEntity;

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

    @Column(name = COLUMN_START_TIME, nullable = false)
    private LocalTime startTime;

    @Column(name = COLUMN_IS_COMPLETED, nullable = false)
    private Boolean isCompleted;

    @Column(name = COLUMN_TARGET_DATE, nullable = false)
    private LocalDate targetDate;

    @Column(name = COLUMN_ORDER, nullable = false)
    private int order;

}
