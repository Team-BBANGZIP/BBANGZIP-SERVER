package org.sopt.user.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.user.exception.InvalidProfileImageKeyException;
import org.sopt.user.exception.UserCoreErrorCode;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DefaultProfileImage {
    IMAGE_1(1, "https://raw.githubusercontent.com/Team-BBANGZIP/BBANGZIP-SERVER/develop/docs/profile-images/1.svg"),
    IMAGE_2(2, "https://raw.githubusercontent.com/Team-BBANGZIP/BBANGZIP-SERVER/develop/docs/profile-images/2.svg"),
    IMAGE_3(3, "https://raw.githubusercontent.com/Team-BBANGZIP/BBANGZIP-SERVER/develop/docs/profile-images/3.svg"),
    IMAGE_4(4, "https://raw.githubusercontent.com/Team-BBANGZIP/BBANGZIP-SERVER/develop/docs/profile-images/4.svg"),
    IMAGE_5(5, "https://raw.githubusercontent.com/Team-BBANGZIP/BBANGZIP-SERVER/develop/docs/profile-images/5.svg"),
    IMAGE_6(6, "https://raw.githubusercontent.com/Team-BBANGZIP/BBANGZIP-SERVER/develop/docs/profile-images/6.svg");

    private final int key;
    private final String url;

    public static String getUrlByKey(int key) {
        return Arrays.stream(values())
                .filter(img -> img.key == key)
                .findFirst()
                .orElseThrow(() -> new InvalidProfileImageKeyException(UserCoreErrorCode.INVALID_PROFILE_IMAGE_KEY))
                .getUrl();
    }
}
