package minesweeper.utility;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MessageUtility {
    private static final Random random = new Random();

    public static Optional<String> getMessageForLoser() {
        List<String> messages = List.of(
            "Оп! І всьо!",
            "Ой, лишенько!",
            "Життя потрачено!",
            "Кінець гри!",
            "Програш",
            "Чому ж ти такий необережни?",
            "Ти підірвався! Наступного разу будь обережнішим!",
            "Спробуй ще раз! Можливо, пощастить",
            "Україна пам'ятає своїх героїв!",
            "Про мертвих або добре, або нічого",
            "Не твоє це діло! Знайди іншу справу!",
            "Не лізь, бо вб'є! А. Вже убило",
            "Вся твоя інформація тепер в інтернеті",
            "Куди ж ти преш!",
            "Щось смаленим запахло",
            "Бідний! Як же ти тепер без ніг ходитимеш?",
            "Ех! Я думав, що ти зможеш! А ти...",
            "Сім раз подумай, а один натисни!"
        );

        return getRandomMessage(messages);
    }

    public static Optional<String> getMessageForWinner() {
        List<String> messages = List.of(
            "Молодець!",
            "Перемога",
            "Наш хлопець!",
            "Відразу видно, що козак!",
            "Оце по нашому!",
            "Гарне діло робиш!",
            "Вітаю! Ти впорався!",
            "Так тримати!",
            "Нумо ще раз це зробимо!",
            "Ти зміг! Я в тобі не сумнівався!",
            "От би всі були такими як ти!",
            "Краще ніж ти ніхто б не зробив!",
            "Професійна робота!",
            "Діло рук майстра!",
            "Де ти такому навчився?",
            "Друже, в чому твій секрет?",
            "Ти всіх врятував!",
            "Ти мій герой!"
        );

        return getRandomMessage(messages);
    }

    private static Optional<String> getRandomMessage(List<String> messages) {
        int index = random.nextInt(messages.size());
        return Optional.ofNullable(messages.get(index));
    }
}
