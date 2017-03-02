package sg.kata.score;
import sg.kata.model.Score;
import sg.kata.model.SetScore;
import sg.kata.score.byindex.SetScoreByPlayerIndex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetScoreExecutor extends ScoreExecutor{
    final static Logger log = LogManager.getLogger(SetScoreExecutor.class);
    protected SetScoreByPlayerIndex scoreByPlayerIndex;
    public SetScoreExecutor(SetScore score){
        super(score);
        this.scoreByPlayerIndex = new SetScoreByPlayerIndex(score);
    }

    public SetScoreByPlayerIndex getScoreByPlayerIndex() {
        return scoreByPlayerIndex;
    }

    @Override
    public void initTheScore(){
        super.initTheScore();
        Score score = scoreByPlayerIndex.getScore();
        score.setScoreForPlayer1(0);
        score.setScoreForPlayer2(0);
    }

    @Override
    public void applyRules(int playerIndex){

        int indexOfOtherPlayer = 1 - playerIndex;
        int currentScore = scoreByPlayerIndex.getScore(playerIndex);
        int newScore = nextScore(currentScore);

        if ( newScore >= 6 )
            if ( newScore - scoreByPlayerIndex.getScore(indexOfOtherPlayer) >= 2 ) {
                setPlayerHasWon(true);
            }else
                log.debug( "Score gap < 2 -> Tie-Break continue ..." );
        scoreByPlayerIndex.setScore( playerIndex, newScore );
        log.debug( "New Set Score " + scoreByPlayerIndex.getScore() );

    }

    @Override
    protected int nextScore(int score){
        return score+1;
    }

}