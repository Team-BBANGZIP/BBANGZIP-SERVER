package org.sopt.bread.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.sopt.bread.domain.BreadTableConstants.COLUMN_ID;


@Entity
@Getter
@Table(name = BreadTableConstants.TABLE_BREAD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BreadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Long id;

    @Column(name = BreadTableConstants.COLUMN_NAME, nullable = false)
    private String name;

    @Column(name = BreadTableConstants.COLUMN_IMAGE_URL, nullable = false)
    private String imageUrl;

    @Column(name = BreadTableConstants.COLUMN_REQUIRED_COUNT, nullable = false)
    private int requiredCount;

}
