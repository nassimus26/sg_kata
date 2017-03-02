package sg.kata.model;

/**
 * Created by nassimus on 09/07/2016.
 */
public class GameScore extends Score {

    private boolean deuceForPlayer1;
    private boolean deuceForPlayer2;


    public GameScore(){

    }

    public boolean isDeuceForPlayer1() {
        return deuceForPlayer1;
    }

    public void setDeuceForPlayer1(boolean deuceForPlayer1) {
        this.deuceForPlayer1 = deuceForPlayer1;
    }

    public boolean isDeuceForPlayer2() {
        return deuceForPlayer2;
    }

    public void setDeuceForPlayer2(boolean deuceForPlayer2) {
        this.deuceForPlayer2 = deuceForPlayer2;
    }

    @Override
    public String toString() {
        return "GameScore{" +
                "Player1(" + getGame().getPlayer1().getName()+")={score=" + getScoreForPlayer1() +", deuce="+deuceForPlayer1+"},"+
                "Player2(" + getGame().getPlayer2().getName()+")={score=" + getScoreForPlayer2() +", deuce="+deuceForPlayer2+"}"+
                '}';
    }

}