package sg.kata.game;

import sg.kata.model.Game;

/**
 * Created by nassimus on 09/07/2016.
 */
public class HumanTennisGame extends TennisGame {

    public HumanTennisGame(Game game) {
        super( game );
    }

    public void winAPoint(int index) {
        setPointWinner(getPlayers().get(index));
        applyGameScoreRules(getPointWinner());
    }

}