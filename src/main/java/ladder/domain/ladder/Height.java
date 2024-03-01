package ladder.domain.ladder;

public class Height {

    private static final int MIN_HEIGHT = 1;

    private final int value;

    public Height(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MIN_HEIGHT) {
            throw new IllegalArgumentException("사다리 높이는 1 이상이어야 합니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
