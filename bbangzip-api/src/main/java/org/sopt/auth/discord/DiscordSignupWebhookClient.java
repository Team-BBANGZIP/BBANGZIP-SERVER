package org.sopt.auth.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.text.NumberFormat;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class DiscordSignupWebhookClient {

    private static final int CONNECT_TIMEOUT_MS = 2_000;
    private static final int READ_TIMEOUT_MS = 3_000;

    private static final String WEBHOOK_DISPLAY_NAME = "BBANGZIP USER BOT";
    private static final String EMBED_TITLE = "🍞 신규 회원 가입";

    /** 알림마다 다른 톤 — 빵·크림 계열 (디스코드 embed color = RGB 정수) */
    private static final int[] EMBED_COLOR_PALETTE = {
            0xC4B5A0, 0xD4B896, 0xE8C4A0, 0xC9A87C, 0xB8A88A,
            0xDCC7A1, 0xE6D4B8, 0xC5B358, 0xDEB887, 0xBC9A8A,
            0xD2A679, 0xC4A574, 0xE5D4C4,
    };

    private final DiscordSignupProperties properties;
    private final RestClient restClient;

    public DiscordSignupWebhookClient(DiscordSignupProperties properties) {
        this.properties = properties;
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(CONNECT_TIMEOUT_MS);
        factory.setReadTimeout(READ_TIMEOUT_MS);
        this.restClient = RestClient.builder().requestFactory(factory).build();
    }

    public void notifySignUp(long userId, String nickname) {
        if (!properties.hasWebhook()) {
            return;
        }
        String safeNickname = sanitizeForDiscord(nickname);
        DiscordWebhookPayload payload = buildSignupPayload(userId, safeNickname);
        try {
            restClient
                    .post()
                    .uri(properties.webhookUrl())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            log.warn("Discord signup webhook request failed userId={}", userId, e);
        }
    }

    private static String sanitizeForDiscord(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            return "(없음)";
        }
        String trimmed = nickname.trim();
        if (trimmed.length() > 64) {
            trimmed = trimmed.substring(0, 64) + "…";
        }
        return trimmed.replace("`", "'").replace("\n", " ");
    }

    private static DiscordWebhookPayload buildSignupPayload(long userId, String safeNickname) {
        Instant occurredAt = Instant.now();
        String ordinal = NumberFormat.getNumberInstance(Locale.KOREA).format(userId);
        String description = String.format(
                "%s님이 빵집의 %s번째 유저가 되었어요.",
                safeNickname,
                ordinal
        );
        int color = EMBED_COLOR_PALETTE[ThreadLocalRandom.current().nextInt(EMBED_COLOR_PALETTE.length)];

        SignupEmbed embed = new SignupEmbed(
                EMBED_TITLE,
                description,
                color,
                occurredAt.toString()
        );
        return new DiscordWebhookPayload(WEBHOOK_DISPLAY_NAME, List.of(embed));
    }

    private record DiscordWebhookPayload(
            @JsonProperty("username") String username,
            @JsonProperty("embeds") List<SignupEmbed> embeds
    ) {}

    private record SignupEmbed(
            String title,
            String description,
            Integer color,
            String timestamp
    ) {}
}
