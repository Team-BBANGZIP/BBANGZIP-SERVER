package org.sopt.category.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.common.BaseTimeEntity;
import org.sopt.user.domain.UserEntity;

import static org.sopt.category.domain.CategoryTableConstants.*;

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

    @Column(name = COLUMN_COLOR, nullable = false)
    private String color;

    @Column(name = COLUMN_IS_VISIBLE, nullable = false)
    private Boolean isVisible;

    @Column(name = COLUMN_ORDER, nullable = false)
    private int order;

}
