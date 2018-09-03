package sg.kata.domain.score;
import lombok.extern.slf4j.Slf4j;
import sg.kata.domain.model.IGame;

@Slf4j
public class SetScoreExecutor extends ScoreExecutor {

    public SetScoreExecutor(IGame game){
        super(game);
    }

    @Override
    public void applyRules(String player){

        String opponent = game.getOpponentName(player);
        int currentScore = game.getPlayerSetScore(player);
        int newScore = nextScore(currentScore);

        if ( newScore >= 6 )
            if ( newScore - game.getPlayerSetScore(opponent) >= 2 ) {
                game.winTheSet(player);
            }else
                log.debug( "Score gap < 2 -> Tie-Break continue ..." );
        game.setPlayerSetScore( player, newScore );
        log.debug( "New Set Score " + game.serialize() );

    }

    @Override
    protected int nextScore(int score){
        return score+1;
    }

}