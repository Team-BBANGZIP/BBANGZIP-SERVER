package org.sopt.auth.discord;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DiscordSignupProperties.class)
public class DiscordSignupConfig {}
