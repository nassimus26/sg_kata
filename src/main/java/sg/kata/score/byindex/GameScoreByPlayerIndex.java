package sg.kata.score.byindex;

import sg.kata.model.GameScore;

/**
 * Created by nassimus on 10/07/2016.
 */
public class GameScoreByPlayerIndex extends ScoreByPlayerIndex {

    public GameScoreByPlayerIndex(GameScore score){
        super(score);
    }

    public boolean isDeuce(int index){
        if (index==0)
            return ((GameScore)score).isDeuceForPlayer1();
        else if (index==1)
            return ((GameScore)score).isDeuceForPlayer2();
        else
            throw new RuntimeException("Invalid index");
    }

    public void setDeuce(int index, boolean score_){
        if (index==0)
            ((GameScore)score).setDeuceForPlayer1(score_);
        else if (index==1)
            ((GameScore)score).setDeuceForPlayer2(score_);
        else
            throw new RuntimeException("Invalid index");
    }

}