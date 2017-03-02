package sg.kata.console;

import sg.kata.game.TennisGame;
import sg.kata.model.Game;
import sg.kata.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sg.kata.model.Score;
import sg.kata.model.GameScore;
import sg.kata.score.byindex.ScoreByPlayerIndex;
import sg.kata.score.byindex.GameScoreByPlayerIndex;

/**
 * Created by nassimus on 09/07/2016.
 */
public abstract class TennisGameConsole {

    final static Logger log = LogManager.getLogger(TennisGame.class);

    protected TennisGame game;

    public void initPlayersAndScores(){
        log.debug("Init Players and scores");
        game.getModel().setPlayer1(null);
        game.getModel().setPlayer2(null);
        game.getModel().getGameScore().setScoreForPlayer1(0);
        game.getModel().getGameScore().setScoreForPlayer2(0);
        game.getModel().getGameScore().setDeuceForPlayer1(false);
        game.getModel().getGameScore().setDeuceForPlayer2(false);
    }
    public void joinTheGame(Player player){
        if(game.getModel().getPlayer1()==null) {
            game.getModel().setPlayer1(player);
            log.debug(player+" has join the game as player1");
        }else
            if(game.getModel().getPlayer2()==null) {
                game.getModel().setPlayer2(player);
                log.debug(player+" has join the game as player2");
            }else
                throw new RuntimeException("Game is full, no empty place avalaible");
    }

    public void startThePoint(){
        game.startAPoint();
    }

    public void startTheSet(){
        game.startASet();
    }

    public Player getSetWinner(){
        return game.getSetWinner();
    }


    public Player getSetLooser(){
        int winnerIndex = game.getPlayerIndex(getSetWinner());
        int looserIndex = 1 - winnerIndex;
        return game.getPlayers().get(looserIndex);
    }

    public int getSetWinnerScore(){
        int winnerIndex = game.getPlayerIndex(getSetWinner());
        return game.getGameScoreByIndex().getScore(winnerIndex);
    }

    public int getSetLooserScore(){
        int winnerIndex = game.getPlayerIndex(getSetLooser());
        return game.getGameScoreByIndex().getScore(winnerIndex);
    }

    public Player getPointWinner(){
        return game.getPointWinner();
    }

    public Game getGameModel(){
        return game.getModel();
    }

    public Score getSetScore(){
        return game.getGameScore();
    }

    public GameScore getGameScore(){
        return game.getGameScore();
    }

    public GameScoreByPlayerIndex getGameScoreByIndex(){
        return game.getGameScoreByIndex();
    }

}
