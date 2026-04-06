package org.sopt.auth.discord;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discord.signup")
public record DiscordSignupProperties(String webhookUrl) {

    public boolean hasWebhook() {
        return webhookUrl != null && !webhookUrl.isBlank();
    }
}
