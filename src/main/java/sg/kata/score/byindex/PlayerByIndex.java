package sg.kata.score.byindex;

import sg.kata.model.Game;
import sg.kata.model.Player;

/**
 * Created by nassimus on 10/07/2016.
 */
public class PlayerByIndex {

    private Game game;

    public PlayerByIndex(Game game){
        this.game = game;
    }
    public Player getPlayer(int index){
        if (index==0)
            return game.getPlayer1();
        else
        if (index==1)
            return game.getPlayer2();
        else
            throw new RuntimeException("Invalid index");
    }

}