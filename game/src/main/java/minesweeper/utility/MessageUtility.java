package minesweeper.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MessageUtility {
    private static final Random random = new Random();

    public static Optional<String> getMessageForLoser() {
        List<String> messages = new ArrayList<>();
        messages.add("Оп! І всьо!");
        messages.add("Ой, лишенько!");
        messages.add("Життя потрачено!");
        messages.add("Кінець гри!");
        messages.add("Програш");
        messages.add("Чому ж ти такий необережни?");
        messages.add("Ти підірвався! Наступного разу будь обережнішим!");
        messages.add("Спробуй ще раз! Можливо, пощастить");
        messages.add("Україна пам'ятає своїх героїв!");
        messages.add("Про мертвих або добре, або нічого");
        messages.add("Не твоє це діло! Знайди іншу справу!");
        messages.add("Не лізь, бо вб'є! А. Вже убило");
        messages.add("Вся твоя інформація тепер в інтернеті");
        messages.add("Куди ж ти преш!");
        messages.add("Щось смаленим запахло");
        messages.add("Бідний! Як же ти тепер без ніг ходитимеш?");
        messages.add("Ех! Я думав, що ти зможеш! А ти...");
        messages.add("Сім раз подумай, а один натисни!");

        return getRandomMessage(messages);
    }

    public static Optional<String> getMessageForWinner() {
        List<String> messages = new ArrayList<>();
        messages.add("Молодець!");
        messages.add("Перемога");
        messages.add("Наш хлопець!");
        messages.add("Відразу видно, що козак!");
        messages.add("Оце по нашому!");
        messages.add("Гарне діло робиш!");
        messages.add("Вітаю! Ти впорався!");
        messages.add("Так тримати!");
        messages.add("Нумо ще раз це зробимо!");
        messages.add("Ти зміг! Я в тобі не сумнівався!");
        messages.add("От би всі були такими як ти!");
        messages.add("Краще ніж ти ніхто б не зробив!");
        messages.add("Професійна робота!");
        messages.add("Діло рук майстра!");
        messages.add("Де ти такому навчився?");
        messages.add("Друже, в чому твій секрет?");
        messages.add("Ти мій герой!");

        return getRandomMessage(messages);
    }

    private static Optional<String> getRandomMessage(List<String> messages) {
        int index = random.nextInt(messages.size());
        return Optional.ofNullable(messages.get(index));
    }
}
