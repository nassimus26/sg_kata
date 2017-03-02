package sg.kata.console;

import sg.kata.game.HumanTennisGame;
import sg.kata.model.Game;

/**
 * Created by nassimus on 09/07/2016.
 */
public class HumanTennisGameConsole extends TennisGameConsole {

    public HumanTennisGameConsole(Game game){
        this.game = new HumanTennisGame(game);
    }
    public HumanTennisGameConsole(){//players will join the game later
        this.game = new HumanTennisGame(new Game());
    }

    public void winAPoint(int playerIndex){
        game.checkSetIsStarted();
        ((HumanTennisGame)game).winAPoint(playerIndex);
    }

}
