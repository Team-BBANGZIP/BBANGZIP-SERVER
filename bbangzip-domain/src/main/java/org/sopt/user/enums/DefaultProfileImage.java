package org.sopt.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.user.exception.InvalidProfileImageKeyException;
import org.sopt.user.exception.UserCoreErrorCode;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DefaultProfileImage {
    IMAGE_0(0, "https://github.com/user-attachments/assets/6766644a-fb80-4ef1-b824-e10632959a0f"),
    IMAGE_1(1, "https://github.com/user-attachments/assets/1e30cf27-c284-40ee-9a03-8bfc51915ea0"),
    IMAGE_2(2, "https://github.com/user-attachments/assets/50cb72c6-eb18-449d-a7bd-aba56e094997"),
    IMAGE_3(3, "https://github.com/user-attachments/assets/ba5296a5-d92f-431f-9bff-c3decd01fe52"),
    IMAGE_4(4, "https://github.com/user-attachments/assets/33a11948-d36d-49e2-9e69-d6d2885f61dd"),
    IMAGE_5(5, "https://github.com/user-attachments/assets/f5dae521-16c0-4557-a4ba-7cf4268cc9bd"),
    IMAGE_6(6, "https://github.com/user-attachments/assets/e3e7a2c0-eba7-4065-a3b4-97e3f1e673d7");

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