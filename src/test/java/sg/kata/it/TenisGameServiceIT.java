package sg.kata.it;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sg.kata.infrastructure.services.TennisGameService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nassimus on 11/07/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TenisGameServiceIT {
    @Autowired
    private TennisGameService gameService;

    private static String default_winner = "player1";
    private static String default_looser = "player2";

    @Before
    public void start(){
        gameService.resetGame();
        gameService.joinTheGame(default_winner);
        gameService.joinTheGame(default_looser);
    }

    @Test
    public void testPlayTowGames() {
        playAGame(default_winner, false, 1, 0);
        playAGame(default_winner, false, 2, 0);
    }

    @Test
    public void testGameWinner(){
        playAGame(false);
        assertThat(gameService.getGameWinner()).isNotNull();
        assertThat(gameService.getGameWinner().getName()).isEqualTo(default_winner);
    }

    @Test
    public void testPointWinner(){
        gameService.winThePoint(default_winner);
        assertThat(gameService.getPointWinner().getName()).isEqualTo(default_winner);
    }

    @Test
    public void testSetWinner(){// we need 6 points

        playAGame(default_winner, false, 1, 0);
        playAGame(default_winner, false, 2, 0);
        playAGame(default_winner, false, 3, 0);
        playAGame(default_winner, false, 4, 0);
        playAGame(default_winner, false, 5, 0);
        playAGame(default_winner, false, 6, 0);

        assertThat(gameService.getSetWinner()).isNotNull();
        assertThat(gameService.getSetWinner().getName()).isEqualTo(default_winner);
    }

    @Test
    public void testDeuceCase(){
        playAGame(true);
    }

    @Test
    public void testWinTheSet(){// we need 6 points

        playAGame(default_looser, false, 1, 0);
        playAGame(default_looser, false, 2, 0);
        playAGame(default_looser, false, 3, 0);
        playAGame(default_looser, false, 4, 0);

        playAGame(default_winner, false, 1, 4);
        playAGame(default_winner, false, 2, 4);
        playAGame(default_winner, false, 3, 4);
        playAGame(default_winner, false, 4, 4);
        playAGame(default_winner, false, 5, 4);
        playAGame(default_winner, false, 6, 4);

        assertThat(gameService.getSetWinner()).isNotNull();
        assertThat(gameService.getSetWinner().getName()).isEqualTo(default_winner);

    }

    @Test
    public void testWinTheSet_With_Tie_Break(){// we need 6 points

        playAGame(default_looser, false, 1, 0);
        playAGame(default_looser, false, 2, 0);
        playAGame(default_looser, false, 3, 0);
        playAGame(default_looser, false, 4, 0);
        playAGame(default_looser, false, 5, 0);

        playAGame(default_winner, false, 1, 5);
        playAGame(default_winner, false, 2, 5);
        playAGame(default_winner, false, 3, 5);
        playAGame(default_winner, false, 4, 5);
        playAGame(default_winner, false, 5, 5);
        playAGame(default_winner, false, 6, 5);

        assertThat(gameService.getSetWinner()).isNull();
        assertThat(gameService.getPlayerSetScore(default_looser)).isEqualTo(5);
        assertThat(gameService.getPlayerSetScore(default_winner)).isEqualTo(6);

        playAGame(default_winner, false, 7, 5);

        assertThat(gameService.getSetWinner()).isNotNull();
        assertThat(gameService.getSetWinner().getName()).isEqualTo(default_winner);

    }

    private void playAGame(boolean withDeuceCase){
        playAGame(default_winner, withDeuceCase, 1, 0);
    }

    private void playAGame(String winner, boolean withDeuceCase, int expectedWinnerSetScore, int expectedLooserSetScore){
        String looser = gameService.getOpponentName(winner);
        gameService.winThePoint(winner);
        assertThat(gameService.getPlayerGameScore(winner)).isEqualTo(15);
        assertThat(gameService.getPlayerGameScore(looser)).isEqualTo(0);
        assertThat(gameService.isPlayerDeuce(winner)).isFalse();
        assertThat(gameService.isPlayerDeuce(looser)).isFalse();

        gameService.winThePoint(looser);
        assertThat(gameService.getPlayerGameScore(winner)).isEqualTo(15);
        assertThat(gameService.getPlayerGameScore(looser)).isEqualTo(15);
        assertThat(gameService.isPlayerDeuce(winner)).isFalse();
        assertThat(gameService.isPlayerDeuce(looser)).isFalse();

        gameService.winThePoint(looser);
        assertThat(gameService.getPlayerGameScore(winner)).isEqualTo(15);
        assertThat(gameService.getPlayerGameScore(looser)).isEqualTo(30);
        assertThat(gameService.isPlayerDeuce(winner)).isFalse();
        assertThat(gameService.isPlayerDeuce(looser)).isFalse();

        gameService.winThePoint(winner);
        assertThat(gameService.getPlayerGameScore(winner)).isEqualTo(30);
        assertThat(gameService.getPlayerGameScore(looser)).isEqualTo(30);
        assertThat(gameService.isPlayerDeuce(winner)).isFalse();
        assertThat(gameService.isPlayerDeuce(looser)).isFalse();

        gameService.winThePoint(winner);
        assertThat(gameService.getPlayerGameScore(winner)).isEqualTo(40);
        assertThat(gameService.getPlayerGameScore(looser)).isEqualTo(30);
        assertThat(gameService.isPlayerDeuce(winner)).isFalse();
        assertThat(gameService.isPlayerDeuce(looser)).isFalse();

        if (withDeuceCase) {

            gameService.winThePoint(looser);
            assertThat(gameService.getPlayerGameScore(winner)).isEqualTo(40);
            assertThat(gameService.getPlayerGameScore(looser)).isEqualTo(40);
            assertThat(gameService.isPlayerDeuce(winner)).isFalse();
            assertThat(gameService.isPlayerDeuce(looser)).isFalse();

            gameService.winThePoint(looser);
            assertThat(gameService.getPlayerGameScore(winner)).isEqualTo(40);
            assertThat(gameService.getPlayerGameScore(looser)).isEqualTo(40);
            assertThat(gameService.isPlayerDeuce(winner)).isFalse();
            assertThat(gameService.isPlayerDeuce(looser)).isTrue();

            gameService.winThePoint(winner);
            assertThat(gameService.getPlayerGameScore(winner)).isEqualTo(40);
            assertThat(gameService.getPlayerGameScore(looser)).isEqualTo(40);
            assertThat(gameService.isPlayerDeuce(winner)).isFalse();
            assertThat(gameService.isPlayerDeuce(looser)).isFalse();

            gameService.winThePoint(winner);
            assertThat(gameService.getPlayerGameScore(winner)).isEqualTo(40);
            assertThat(gameService.getPlayerGameScore(looser)).isEqualTo(40);
            assertThat(gameService.isPlayerDeuce(winner)).isTrue();
            assertThat(gameService.isPlayerDeuce(looser)).isFalse();

            gameService.winThePoint(winner);
            assertThat(gameService.getPlayerSetScore(winner)).isEqualTo(expectedWinnerSetScore);
            assertThat(gameService.getPlayerSetScore(looser)).isEqualTo(expectedLooserSetScore);

        }else{
            gameService.winThePoint(winner);
            assertThat(gameService.getPlayerSetScore(winner)).isEqualTo(expectedWinnerSetScore);
            assertThat(gameService.getPlayerSetScore(looser)).isEqualTo(expectedLooserSetScore);
        }
    }

}
