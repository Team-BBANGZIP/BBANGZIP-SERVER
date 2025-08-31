package org.sopt.category.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.sopt.category.exception.CategoryCoreErrorCode;
import org.sopt.category.exception.CategoryInvalidColorException;

public enum CategoryColor {
    RED1,
    RED2,
    YELLOW1,
    YELLOW2,
    GREEN1,
    GREEN2,
    BLUE1,
    BLUE2,
    PURPLE1,
    PURPLE2;

    @JsonCreator
    public static CategoryColor from(String value) {
        try {
            return CategoryColor.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CategoryInvalidColorException(CategoryCoreErrorCode.INVALID_CATEGORY_COLOR);
        }
    }
}
