package ladder.domain.ladder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class LineTest {

    @DisplayName("라인이 비어있으면 예외 발생")
    @Test
    void validateTest_WhenLineIsEmpty() {
        List<Stick> sticks = Collections.emptyList();

        assertThatThrownBy(() -> new Line(sticks))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("적어도 가로 라인이 하나 이상 있어야 한다.");
    }

    @DisplayName("라인이 null인 경우 예외 발생")
    @Test
    void validateTest_WhenLineIsNull() {

        assertThatThrownBy(() -> new Line(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("적어도 가로 라인이 하나 이상 있어야 한다.");
    }

    @DisplayName("가로 라인이 겹칠 경우, 예외 발생")
    @Test
    void validateOverlappedRowLine() {
        List<Stick> sticks = List.of(Stick.EXISTENCE, Stick.EXISTENCE, Stick.NON_EXISTENCE);

        assertThatThrownBy(() -> new Line(sticks))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가로 라인이 이어지면 안된다.");
    }

    @DisplayName("해당 라인을 지나고 나서 플레이어의 위치 결과를 알 수 있다")
    @ParameterizedTest
    @CsvSource({"0, 0", "1, 2", "2, 1"})
    void findNextPositionTest(int playerPosition, int expected) {
        Line line = new Line(List.of(Stick.NON_EXISTENCE, Stick.EXISTENCE));

        int actual = line.findNextPosition(playerPosition);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("플레이어의 위치가 사다리 범위에서 벗어날 경우, 예외를 던진다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 3})
    void findNextPositionTest_WhenOutOfBounds_ThrowException(int playerPosition) {
        Line line = new Line(List.of(Stick.NON_EXISTENCE, Stick.EXISTENCE));

        assertThatThrownBy(() -> line.findNextPosition(playerPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("플레이어의 위치가 사다리를 벗어났습니다.");
    }

    @DisplayName("해당 위치에 가로 라인이 존재하는 지 테스트")
    @Test
    void isExistTest() {
        Line line = new Line(List.of(Stick.EXISTENCE, Stick.NON_EXISTENCE, Stick.EXISTENCE));

        assertAll(
                () -> assertThat(line.isExist(0)).isTrue(),
                () -> assertThat(line.isExist(1)).isFalse()
        );
    }

    @DisplayName("위치가 0 이하이거나 라인의 사이즈보다 클 경우, 예외를 발생시킨다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 3, 4})
    void isExistTest_whenWidthIsOutOfRange(int position) {
        Line line = new Line(List.of(Stick.EXISTENCE, Stick.NON_EXISTENCE, Stick.EXISTENCE));

        assertThatThrownBy(() -> line.isExist(position))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("길이 위치가 범위를 벗어났습니다 Index : %d, Size : %d".formatted(position, 3));
    }

    @DisplayName("라인의 사이즈를 구할 수 있는지 테스트")
    @Test
    void sizeTest() {
        Line line = new Line(List.of(Stick.EXISTENCE, Stick.NON_EXISTENCE, Stick.EXISTENCE));

        assertThat(line.getWidth()).isEqualTo(3);
    }
}
