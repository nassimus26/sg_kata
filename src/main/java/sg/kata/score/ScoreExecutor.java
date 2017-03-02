package sg.kata.score;

import sg.kata.model.Score;

/**
 * Created by nassimus on 09/07/2016.
 */
public abstract class ScoreExecutor {
    private Score score;
    public Score getScore(){
        return score;
    }

    public ScoreExecutor(Score score){
        this.score = score;
    }

    private boolean playerHasWon;

    public void setPlayerHasWon(boolean playerHasWon) {
        this.playerHasWon = playerHasWon;
    }

    public boolean playerHasWon(){
        return playerHasWon;
    }

    public abstract void applyRules(int playerIndex);

    protected abstract int nextScore(int score);

    public void initTheScore(){
        setPlayerHasWon(false);
    }

}