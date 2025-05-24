package org.sopt.todo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.common.BaseTimeEntity;
import java.time.LocalDateTime;

import static org.sopt.todo.domain.TodoTableConstants.*;

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
    private LocalDateTime startTime;

    @Column(name = COLUMN_IS_COMPLETED, nullable = false)
    private Boolean isCompleted;

    @Column(name = COLUMN_TARGET_DATE, nullable = false)
    private LocalDateTime targetDate;

    @Column(name = COLUMN_ORDER, nullable = false)
    private int order;

}
