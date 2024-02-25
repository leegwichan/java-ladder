package ladder.domain.linegenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import ladder.domain.Stick;

public class LinePatternGenerator implements LineGenerator {

    private static final int MIN_PLAYERS = 2;

    private final BooleanSupplier supplier;

    public LinePatternGenerator(BooleanSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public List<Stick> generate(int countOfPlayers) {
        validate(countOfPlayers);
        List<Stick> line = new ArrayList<>();
        int width = countOfPlayers - 1;

        while (line.size() < width) {
            add(line);
        }
        return line;
    }

    private void validate(int countOfPlayers) {
        if (countOfPlayers < MIN_PLAYERS) {
            throw new IllegalArgumentException("사다리의 크기는 2 이상입니다");
        }
    }

    private void add(List<Stick> line) {
        if (isNotOverlapped(line)) {
            line.add(generateStick());
            return;
        }
        line.add(Stick.NON_EXISTENCE);
    }

    private boolean isNotOverlapped(List<Stick> line) {
        return line.isEmpty() || !line.get(line.size() - 1).isExist();
    }

    private Stick generateStick() {
        if (supplier.getAsBoolean()) {
            return Stick.EXISTENCE;
        }
        return Stick.NON_EXISTENCE;
    }
}
