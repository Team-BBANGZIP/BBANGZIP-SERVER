package org.sopt.auth.discord;

import lombok.RequiredArgsConstructor;
import org.sopt.auth.event.SignUpCompletedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SignUpCompletedDiscordListener {

    private final DiscordSignupWebhookClient discordSignupWebhookClient;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onSignUpCompleted(SignUpCompletedEvent event) {
        discordSignupWebhookClient.notifySignUp(event.userId(), event.nickname());
    }
}
