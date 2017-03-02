package sg.tests;

import sg.kata.console.AutoTennisGameConsole;
import org.junit.Assert;
import org.junit.Test;
import sg.tests.data.DefaultTestingData;

/**
 * Created by nassimus on 11/07/2016.
 */
public class AutoGameTest {
    @Test
    public void testAutoPlayASet(){// print an automatic play log
        AutoTennisGameConsole gameConsole = new AutoTennisGameConsole();
        gameConsole.joinTheGame(DefaultTestingData.newPlayer1());
        gameConsole.joinTheGame(DefaultTestingData.newPlayer2());
        gameConsole.startTheSet();
        gameConsole.playASet();
        Assert.assertNotNull(gameConsole.getSetWinner());

        Assert.assertEquals(gameConsole.getSetWinnerScore(), 40);
    }
    @Test
    public void testAutoPlayAMatch(){// print an automatic play log
        AutoTennisGameConsole gameConsole = new AutoTennisGameConsole();
        gameConsole.joinTheGame(DefaultTestingData.newPlayer1());
        gameConsole.joinTheGame(DefaultTestingData.newPlayer2());
        gameConsole.startTheMatch();
        gameConsole.playAMatch();
        Assert.assertNotNull(gameConsole.getMatchWinner());

        Assert.assertTrue(gameConsole.getMatchWinnerScore()-gameConsole.getMatchLooserScore()>=2);
        Assert.assertTrue(gameConsole.getMatchWinnerScore()>=6);
    }

}
