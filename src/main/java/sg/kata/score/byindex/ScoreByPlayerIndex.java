package sg.kata.score.byindex;

import sg.kata.model.Player;
import sg.kata.model.Score;

/**
 * Created by nassimus on 10/07/2016.
 */
public abstract class ScoreByPlayerIndex {

    protected Score score;
    private PlayerByIndex playerByIndex;
    public Score getScore() {
        return score;
    }

    public ScoreByPlayerIndex(Score score){
        this.score = score;
        this.playerByIndex = new PlayerByIndex(score.getGame());
    }

    public Player getPlayer(int index){
        return playerByIndex.getPlayer(index);
    }

    public int getScore(int index){
        if (index==0)
            return score.getScoreForPlayer1();
        else if (index==1)
            return score.getScoreForPlayer2();
        else
            throw new RuntimeException("Invalid index");
    }

    public void setScore(int index, int score_){
        if (index==0)
            score.setScoreForPlayer1(score_);
        else if (index==1)
            score.setScoreForPlayer2(score_);
        else
            throw new RuntimeException("Invalid index");
    }

}