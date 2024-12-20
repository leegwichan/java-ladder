package ladder.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import ladder.domain.ladder.Height;
import ladder.domain.ladder.Ladder;
import ladder.domain.ladder.Stick;
import ladder.domain.player.Player;
import ladder.domain.player.Players;
import ladder.domain.product.Product;
import ladder.domain.product.Products;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LadderGameTest {

    static Ladder LADDER_SIZE_3 = Ladder.of(new Height(3), 3, size -> List.of(Stick.EXISTENCE, Stick.NON_EXISTENCE));

    @DisplayName("플레이어 수와 사다리 크기가 맞지 않을 경우, 예외를 던진다")
    @Test
    void validateTest_WhenPlayerSizeNotMatched_ThrowException() {
        Players players = Players.from(List.of("pobi", "crong"));
        Products products = Products.from(List.of("5000", "꽝", "3000"));

        assertThatThrownBy(() -> new LadderGame(LADDER_SIZE_3, players, products))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("플레이어 수와 사다리 길이가 일치하지 않습니다.");
    }

    @DisplayName("상품 수와 사다리 크기가 맞지 않을 경우, 예외를 던진다")
    @Test
    void validateTest_WhenProductSizeNotMatched_ThrowException() {
        Players players = Players.from(List.of("pobi", "crong", "jk"));
        Products products = Products.from(List.of("5000", "꽝", "3000", "꽝"));

        assertThatThrownBy(() -> new LadderGame(LADDER_SIZE_3, players, products))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 수와 사다리 길이가 일치하지 않습니다.");
    }

    @DisplayName("상품 수와 사다리 크기가 맞지 않을 경우, 예외를 던진다")
    @Test
    void validateTest_WhenPlayerAndProductSizeMatched_createObject() {
        Players players = Players.from(List.of("pobi", "crong", "jk"));
        Products products = Products.from(List.of("5000", "꽝", "3000"));

        assertThatCode(() -> new LadderGame(LADDER_SIZE_3, players, products))
                .doesNotThrowAnyException();
    }

    @DisplayName("플레이어 수와 상품 수가 일치하지 않을 경우, 예외를 던진다")
    @Test
    void validateTest_WhenPlayerAndProductSizeNotMatched_ThrowException() {
        Players players = Players.from(List.of("pobi", "crong", "jk"));
        Products products = Products.from(List.of("5000", "꽝", "3000", "꽝"));

        assertThatThrownBy(() -> LadderGame.validate(players, products))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("플레이어 수와 상품 수는 동일해야 합니다.");
    }

    @DisplayName("플레이어 수와 상품 수가 일치하지 않을 경우, 예외를 던지지 않는다.")
    @Test
    void validateTest_WhenPlayerAndProductSizeMatched_doNothing() {
        Players players = Players.from(List.of("pobi", "crong", "jk"));
        Products products = Products.from(List.of("5000", "꽝", "3000"));

        assertThatCode(() -> LadderGame.validate(players, products))
                .doesNotThrowAnyException();
    }

    /*
     *   pobi crong    jk
     *      |-----|     |
     *      |-----|     |
     *      |-----|     |
     *   5000     꽝 3000
     * */
    @DisplayName("사다리 게임의 결과를 반환할 수 있다")
    @Test
    void progressTest() {
        Ladder ladder = Ladder.of(new Height(3), 3, size -> List.of(Stick.EXISTENCE, Stick.NON_EXISTENCE));
        Players players = Players.from(List.of("pobi", "crong", "jk"));
        Products products = Products.from(List.of("5000", "꽝", "3000"));
        LadderGame ladderGame = new LadderGame(ladder, players, products);
        Map<Player, Product> expected = Map.of(new Player("pobi"), new Product("꽝"),
                new Player("crong"), new Product("5000"),
                new Player("jk"), new Product("3000"));

        LadderGameResult actual = ladderGame.progress();

        assertThat(actual.getResults()).isEqualTo(expected);
    }
}
