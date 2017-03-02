package sg.kata.game;

import sg.kata.console.State;
import sg.kata.exception.SetNotStartedOrAlreadyTerminatedException;
import sg.kata.model.GameScore;
import sg.kata.score.GameScoreExecutor;
import sg.kata.score.byindex.GameScoreByPlayerIndex;
import sg.kata.model.Game;
import sg.kata.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TennisGame {
    final static Logger log = LogManager.getLogger(TennisGame.class);
    private Game model;
    private AutoPlayer pointWinner;
    private AutoPlayer setWinner;

    private GameScoreExecutor gameScoreExecutor;
    private Map<Player, AutoPlayer> playersMap;//keep the order the same with the Game sg.kata.model

    private State pointState = State.Default;
    private State gameState = State.Default;
    private State setState = State.Default;

    public GameScore getGameScore(){
        return (GameScore) gameScoreExecutor.getScore();
    }

    public GameScoreByPlayerIndex getGameScoreByIndex(){
        return gameScoreExecutor.getScoreByPlayerIndex();
    }

    public void startAPoint(){
        pointState = State.Started;
        getPlayers().forEach(a->a.setLostThePoint(false));
        this.pointWinner = null;
        log.debug("New Point started");
    }
    public void startASet(){
        setState = State.Started;
        gameScoreExecutor.initTheScore();
        this.setWinner = null;
        log.debug("New set started");
        startAPoint();
    }

    public void checkSetIsStarted(){
        if(!State.Started.equals(setState))
            throw new SetNotStartedOrAlreadyTerminatedException("Set is not started or already terminated");
    }


    public void terminateSet() {
        setState = State.Ended;
        terminateGame();
    }
    public void terminateGame() {
        gameState = State.Ended;
        terminatePoint();
    }
    public void terminatePoint() {
        pointState = State.Ended;
    }

    public boolean isSetStarted() {
        return State.Started.equals(setState);
    }
    public boolean isGameStarted() {
        return State.Started.equals(gameState);
    }
    public boolean isPointStarted() {
        return State.Started.equals(pointState);
    }


    public TennisGame(Game game) {
        this.model = game;
        this.gameScoreExecutor = new GameScoreExecutor(game.getGameScore());
    }

    private Map<Player, AutoPlayer> getPlayersMap(){
        if (playersMap==null) {
            playersMap = new HashMap<>();
            this.playersMap.put(model.getPlayer1(), AutoPlayer.wrap(model.getPlayer1()));
            this.playersMap.put(model.getPlayer2(), AutoPlayer.wrap(model.getPlayer2()));
        }
        return this.playersMap;
    }

    public Game getModel() {
        return model;
    }

    public Player getSetWinner() {
        return setWinner;
    }

    public Player getPointWinner() {
        return pointWinner;
    }

    public void applyGameScoreRules(Player pr){
        AutoPlayer player = getPlayersMap().get(pr);
        int index = getPlayers().indexOf(player);
        gameScoreExecutor.applyRules(index);
        if (gameScoreExecutor.playerHasWon()){
            setWinner = player;
            log.debug( player + " win the set" );
            terminateSet();
        }
    }

    protected void setPointWinner(AutoPlayer pointWinner) {
        this.pointWinner = pointWinner;
    }
    public int getPlayerIndex(Player player){
        if (player.equals(model.getPlayer1()))
            return 0;
        else
        if (player.equals(model.getPlayer2()))
            return 1;
        else
            throw new RuntimeException("Player not founds");
    }
    public List<AutoPlayer> getPlayers() {
        List<AutoPlayer> players = new ArrayList<>();
        Map<Player, AutoPlayer> playersMap = getPlayersMap();
        players.add( playersMap.get(model.getPlayer1()) );
        players.add( playersMap.get(model.getPlayer2()) );
        return players;
    }

    @Override
    public String toString() {
        return "TennisGame{" +
                "sg.kata.model=" + model +
                '}';
    }
}