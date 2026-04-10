package org.sopt.category.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.common.BaseTimeEntity;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.user.domain.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.sopt.category.domain.CategoryTableConstants.COLUMN_COLOR;
import static org.sopt.category.domain.CategoryTableConstants.COLUMN_ID;
import static org.sopt.category.domain.CategoryTableConstants.COLUMN_IS_STOPPED;
import static org.sopt.category.domain.CategoryTableConstants.COLUMN_NAME;
import static org.sopt.category.domain.CategoryTableConstants.COLUMN_ORDER;
import static org.sopt.category.domain.CategoryTableConstants.COLUMN_USER_ID;
import static org.sopt.category.domain.CategoryTableConstants.STOPPED_DATE;
import static org.sopt.category.domain.CategoryTableConstants.TABLE_CATEGORY;

@Entity
@Getter
@Table(name = TABLE_CATEGORY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_USER_ID, nullable = false)
    private UserEntity user;

    @Column(name = COLUMN_NAME, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_COLOR, nullable = false)
    private CategoryColor color;

    @Column(name = COLUMN_IS_STOPPED, nullable = false)
    private boolean isStopped;

    @Column(name = STOPPED_DATE)
    private LocalDateTime stoppedDate;

    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<TodoEntity> todos = new ArrayList<>();

    @Column(name = COLUMN_ORDER, nullable = false)
    private int order;

    public Category toDomain() {
        return Category.builder()
                .id(id)
                .userId(user.getId())
                .name(name)
                .color(color)
                .isStopped(isStopped)
                .order(order)
                .build();
    }

    public static CategoryEntity forCreate(Category category, UserEntity userEntity) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.user = userEntity;
        categoryEntity.name = category.getName();
        categoryEntity.color = category.getColor();
        categoryEntity.isStopped = category.isStopped();
        categoryEntity.order = category.getOrder();
        return categoryEntity;
    }

    public void update(String newName, CategoryColor newColor, Boolean newIsStopped, int order) {
        if (newName != null) this.name = newName;
        if (newColor != null) this.color = newColor;
        if (newIsStopped != null) {
            if (newIsStopped) {
                if (!this.isStopped) {
                    this.stoppedDate = LocalDateTime.now();
                }
            } else {
                this.stoppedDate = null;
            }
            this.isStopped = newIsStopped;
        }
        this.order = order;
    }

    public void updateOrder(int newOrder) {
        this.order = newOrder;
    }

}
