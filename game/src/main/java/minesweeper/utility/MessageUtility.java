package minesweeper.utility;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MessageUtility {
    private static final Random random = new Random();

    public static Optional<String> getMessageForLoser() {
        List<String> messages = List.of(
            "Loser message 1",
            "Loser message 2"
        );

        return getRandomMessage(messages);
    }

    public static Optional<String> getMessageForWinner() {
        List<String> messages = List.of(
            "Winner message 1",
            "Winner message 2"
        );

        return getRandomMessage(messages);
    }

    private static Optional<String> getRandomMessage(List<String> messages) {
        int index = random.nextInt(messages.size());
        return Optional.ofNullable(messages.get(index));
    }
}
