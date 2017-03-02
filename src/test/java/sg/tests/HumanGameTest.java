package sg.tests;

import sg.kata.exception.SetNotStartedOrAlreadyTerminatedException;
import sg.kata.console.HumanTennisGameConsole;
import sg.kata.model.Game;
import sg.kata.model.Score;
import sg.kata.model.GameScore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sg.kata.score.byindex.ScoreByPlayerIndex;
import sg.kata.score.byindex.GameScoreByPlayerIndex;
import sg.tests.data.DefaultTestingData;

/**
 * Created by nassimus on 11/07/2016.
 */
public class HumanGameTest {
    private static HumanTennisGameConsole gameConsole = new HumanTennisGameConsole();
    private static Game game;
    private static GameScore gameScore;
    private static Score setScore;
    private static GameScoreByPlayerIndex gameScoreByPlayerIndex;

    private static int default_winner_player_index = 1;

    @Before
    public void start(){
        gameConsole.initPlayersAndScores();
        gameConsole.joinTheGame(DefaultTestingData.newPlayer1());
        gameConsole.joinTheGame(DefaultTestingData.newPlayer2());
        game = gameConsole.getGameModel();
        setScore = gameConsole.getSetScore();
        gameScore = gameConsole.getGameScore();
        gameScoreByPlayerIndex = gameConsole.getGameScoreByIndex();
    }

    @Test
    public void testPlayTowGames(){
        gameConsole.startTheSet();
        playAGame(default_winner_player_index, 1, 0);
        playAGame(default_winner_player_index, 2, 0);
    }

    @Test
    public void testGameWinner(){
        gameConsole.startTheSet();
        playAGame();
        Assert.assertEquals(gameConsole.getSetWinner(), gameScoreByPlayerIndex.getPlayer(default_winner_player_index));
    }

    @Test
    public void testPointWinner(){
        gameConsole.startTheSet();
        gameConsole.winAPoint(default_winner_player_index);
        Assert.assertEquals(gameConsole.getPointWinner(), gameScoreByPlayerIndex.getPlayer(default_winner_player_index));
    }

    @Test
    public void testSetWinner(){// we need 6 points
        gameConsole.startTheSet();
        playAGame(default_winner_player_index, 1, 0);
        playAGame(default_winner_player_index, 2, 0);
        playAGame(default_winner_player_index, 3, 0);
        playAGame(default_winner_player_index, 4, 0);
        playAGame(default_winner_player_index, 5, 0);
        playAGame(default_winner_player_index, 6, 0);

        Assert.assertEquals(gameConsole.getSetWinner(), gameScoreByPlayerIndex.getPlayer(default_winner_player_index));
    }

    @Test
    public void testPointIncrementation(){
        gameConsole.startTheSet();
        gameConsole.winAPoint(default_winner_player_index);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(default_winner_player_index), 15);
    }

    private void playAGame(){
        playAGame(default_winner_player_index, 1, 0);
    }

    private void playAGame(int winnerIndex, int expectedWinnerSetScore, int expectedLooserSetScore){
        gameConsole.startTheSet();
        int looserIndex = 1 - winnerIndex;
        gameConsole.winAPoint(winnerIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 15);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 0);
        gameConsole.winAPoint(looserIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 15);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 15);
        gameConsole.winAPoint(looserIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 15);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 30);
        gameConsole.winAPoint(winnerIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 30);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 30);
        gameConsole.winAPoint(winnerIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 40);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 30);

        gameConsole.winAPoint(winnerIndex);
        Assert.assertEquals(gameConsole.getSetWinner(), gameScoreByPlayerIndex.getPlayer(default_winner_player_index));

    }

    @Test(expected = SetNotStartedOrAlreadyTerminatedException.class)
    public void triggerSetNotStartedOrAlreadyTerminatedException(){
        gameConsole.winAPoint(default_winner_player_index);
        gameConsole.winAPoint(default_winner_player_index);
        gameConsole.winAPoint(default_winner_player_index);
        gameConsole.winAPoint(default_winner_player_index);// game ends we must call - > gameConsole.startTheGame();
        gameConsole.winAPoint(default_winner_player_index);
    }
}
