package org.sopt.category.domain;

import static org.sopt.category.domain.CategoryTableConstants.COLUMN_COLOR;
import static org.sopt.category.domain.CategoryTableConstants.COLUMN_ID;
import static org.sopt.category.domain.CategoryTableConstants.COLUMN_IS_VISIBLE;
import static org.sopt.category.domain.CategoryTableConstants.COLUMN_NAME;
import static org.sopt.category.domain.CategoryTableConstants.COLUMN_ORDER;
import static org.sopt.category.domain.CategoryTableConstants.COLUMN_USER_ID;
import static org.sopt.category.domain.CategoryTableConstants.TABLE_CATEGORY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.common.BaseTimeEntity;
import org.sopt.user.domain.UserEntity;

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

    @Column(name = COLUMN_IS_VISIBLE, nullable = false)
    private Boolean isVisible;

    @Column(name = COLUMN_ORDER, nullable = false)
    private int order;

    @Builder
    private CategoryEntity(UserEntity user, String name, CategoryColor color, Boolean isVisible, int order) {
        this.user = user;
        this.name = name;
        this.color = color;
        this.isVisible = isVisible;
        this.order = order;
    }


}
