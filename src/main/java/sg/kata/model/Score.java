package sg.kata.model;

/**
 * Created by nassimus on 09/07/2016.
 */
public abstract class Score {

    private int scoreForPlayer1;
    private int scoreForPlayer2;

    private Game game;

    public Score(){

    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getScoreForPlayer1() {
        return scoreForPlayer1;
    }

    public void setScoreForPlayer1(int scoreForPlayer1) {
        this.scoreForPlayer1 = scoreForPlayer1;
    }

    public int getScoreForPlayer2() {
        return scoreForPlayer2;
    }

    public void setScoreForPlayer2(int scoreForPlayer2) {
        this.scoreForPlayer2 = scoreForPlayer2;
    }

    @Override
    public String toString() {
        return "SetScore{" +
                "Player1(" + getGame().getPlayer1().getName()+")={score=" + getScoreForPlayer1() +"},"+
                "Player2(" + getGame().getPlayer2().getName()+")={score=" + getScoreForPlayer2() +"}"+
                '}';
    }

}