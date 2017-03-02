package sg.kata.game;

import sg.kata.model.Game;

import java.util.List;
import java.util.Random;

/**
 * Created by nassimus on 09/07/2016.
 */
public class AutoTennisGame extends TennisGame{

    public AutoTennisGame(Game game) {
        super( game );
    }

    private AutoPlayer findThePointWinner(){
        List<AutoPlayer> players = getPlayers();
        for (AutoPlayer autoPlayer : players)
            if (autoPlayer.hasLostThePoint())
                return getPlayers().stream().filter( p-> !p.hasLostThePoint() ).findAny().orElse(null);
        return null;
    }

    private void playAPoint() {
        int ballPosition = getPlayers().get(new Random().nextInt(1)).sendTheService();
        AutoPlayer pointWinner = findThePointWinner();
        int index = 1;
        while (pointWinner == null) {
            ballPosition = getPlayers().get(index).sendBack(ballPosition);
            pointWinner = findThePointWinner();
            index = 1 - index;
        }
        setPointWinner(pointWinner);

    }

    public void playASet() {
        startASet();
        while (getSetWinner() == null){
            playAPoint();
            applyGameScoreRules(getPointWinner());
            if (getSetWinner()==null)
                startAPoint();
        }
    }

}