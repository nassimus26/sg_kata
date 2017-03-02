package sg.kata.score;

import sg.kata.score.byindex.GameScoreByPlayerIndex;
import sg.kata.model.GameScore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nassimus on 09/07/2016.
 */
public class GameScoreExecutor extends ScoreExecutor{
    final static Logger log = LogManager.getLogger(GameScoreExecutor.class);
    protected GameScoreByPlayerIndex scoreByPlayerIndex;
    public GameScoreExecutor(GameScore score){
        super(score);
        this.scoreByPlayerIndex = new GameScoreByPlayerIndex(score);
    }
    public static List<Integer> STEPS = new ArrayList(Arrays.asList(new Integer[]{0, 15, 30, 40}));

    public GameScoreByPlayerIndex getScoreByPlayerIndex() {
        return scoreByPlayerIndex;
    }

    @Override
    public void initTheScore(){
        super.initTheScore();
        GameScore gameScore = (GameScore) scoreByPlayerIndex.getScore();
        gameScore.setScoreForPlayer1(0);
        gameScore.setScoreForPlayer2(0);
        gameScore.setDeuceForPlayer1(false);
        gameScore.setDeuceForPlayer2(false);
    }
    @Override
    public void applyRules(int playerIndex){
        int indexOfOtherPlayer = 1-playerIndex;
        int currentScore = scoreByPlayerIndex.getScore(playerIndex);
        int newScore = nextScore(currentScore);
        if (currentScore==40) {
            if (scoreByPlayerIndex.getScore(indexOfOtherPlayer)!=40){
                setPlayerHasWon(true);
            }
            else if (scoreByPlayerIndex.isDeuce(playerIndex)) {
                setPlayerHasWon(true);
            }
        }
        if (currentScore == newScore) {
            if (!scoreByPlayerIndex.isDeuce(indexOfOtherPlayer)) {
                scoreByPlayerIndex.setDeuce(playerIndex, true);
                scoreByPlayerIndex.setDeuce(indexOfOtherPlayer, false);
                log.debug("Deuce Case " + scoreByPlayerIndex.getPlayer(playerIndex).getName() + " take the adventage");
            }else{
                scoreByPlayerIndex.setDeuce(indexOfOtherPlayer, false);
                log.debug("Deuce Case " + scoreByPlayerIndex.getPlayer(indexOfOtherPlayer).getName() + " loose the adventage");
            }
        }
        scoreByPlayerIndex.setScore(playerIndex, newScore);
        log.debug("New Game Score : "+scoreByPlayerIndex.getScore());
    }

    @Override
    protected int nextScore(int score){
        int index = STEPS.indexOf(score);
        if ( index < STEPS.size()-1 )
            return STEPS.get(index+1);
        return STEPS.get(index);
    }

}