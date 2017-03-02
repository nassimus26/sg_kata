package sg.kata.console;

import sg.kata.game.AutoTennisGame;
import sg.kata.model.Game;

/**
 * Created by nassimus on 09/07/2016.
 */
public class AutoTennisGameConsole extends TennisGameConsole {

    public AutoTennisGameConsole(Game game){
        this.game = new AutoTennisGame(game);
    }
    public AutoTennisGameConsole(){//players will join the game later
        this.game = new AutoTennisGame(new Game());
    }

    public void playASet(){
        game.checkSetIsStarted();
        ((AutoTennisGame)game).playASet();
    }

    public void playAMatch(){
        game.checkMatchIsStarted();
        ((AutoTennisGame)game).playAMatch();
    }

}