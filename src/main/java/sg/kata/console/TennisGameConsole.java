package sg.kata.console;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sg.kata.game.TennisGame;
import sg.kata.model.*;
import sg.kata.score.byindex.GameScoreByPlayerIndex;
import sg.kata.score.byindex.SetScoreByPlayerIndex;

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
    public void startTheMatch(){
        game.startAMatch();
    }

    public Player getSetWinner(){
        return game.getSetWinner();
    }


    public Player getSetLooser(){
        int winnerIndex = game.getPlayerIndex(getSetWinner());
        int looserIndex = 1 - winnerIndex;
        return game.getPlayers().get(looserIndex);
    }

    public int getMatchWinnerScore(){
        int winnerIndex = game.getPlayerIndex(getMatchWinner());
        return game.getSetScoreByIndex().getScore(winnerIndex);
    }

    public int getMatchLooserScore(){
        int winnerIndex = game.getPlayerIndex(getMatchLooser());
        return game.getSetScoreByIndex().getScore(winnerIndex);
    }

    public Player getMatchWinner(){
        return game.getMatchWinner();
    }
    public Player getMatchLooser(){
        int winnerIndex = game.getPlayerIndex(getMatchWinner());
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

    public SetScore getSetScore(){
        return (SetScore) game.getSetScore();
    }

    public GameScore getGameScore(){
        return game.getGameScore();
    }

    public SetScoreByPlayerIndex getSetSocreByIndex(){
        return game.getSetScoreByIndex();
    }

    public GameScoreByPlayerIndex getGameScoreByIndex(){
        return game.getGameScoreByIndex();
    }

}
