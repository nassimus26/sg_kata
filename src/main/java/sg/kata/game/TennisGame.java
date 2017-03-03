package sg.kata.game;

import sg.kata.console.State;
import sg.kata.exception.SetNotStartedOrAlreadyTerminatedException;
import sg.kata.model.*;
import sg.kata.score.SetScoreExecutor;
import sg.kata.score.GameScoreExecutor;
import sg.kata.score.byindex.SetScoreByPlayerIndex;
import sg.kata.score.byindex.GameScoreByPlayerIndex;
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
    private AutoPlayer matchWinner;

    private GameScoreExecutor gameScoreExecutor;
    private SetScoreExecutor setScoreExecutor;
    private Map<Player, AutoPlayer> playersMap;//keep the order the same with the Game sg.kata.model

    private State pointState = State.Default;
    private State gameState = State.Default;
    private State setState = State.Default;
    private State matchState = State.Default;

    public GameScore getGameScore(){
        return (GameScore) gameScoreExecutor.getScore();
    }
    public Score getSetScore(){
        return setScoreExecutor.getScore();
    }

    public GameScoreByPlayerIndex getGameScoreByIndex(){
        return gameScoreExecutor.getScoreByPlayerIndex();
    }
    public SetScoreByPlayerIndex getSetScoreByIndex(){
        return setScoreExecutor.getScoreByPlayerIndex();
    }

    public void startAPoint(){
        checkMatchIsReady();
        pointState = State.Started;
        getPlayers().forEach(a->a.setLostThePoint(false));
        this.pointWinner = null;
    }
    public void startASet(){
        checkMatchIsReady();
        if (!setState.equals(State.Started))
            log.debug("New set started");
        setState = State.Started;
        gameScoreExecutor.initTheScore();
        this.setWinner = null;
        startAPoint();
    }

    public void startAMatch(){
        checkMatchIsReady();
        setScoreExecutor.initTheScore();
        matchState = State.Started;
        matchWinner = null;
        log.debug(model+" started");
        startASet();
    }

    public void checkSetIsStarted(){
        if(!State.Started.equals(setState))
            throw new SetNotStartedOrAlreadyTerminatedException("Set is not started or already terminated");
    }

    public void checkMatchIsStarted(){
        if(!State.Started.equals(matchState))
            throw new RuntimeException("Match is not started or already terminated");
    }

    public void terminateMatch() {
        matchState = State.Ended;
        terminateSet();
    }
    public void terminateSet() {
        setState = State.Ended;
        terminateGame();
        log.debug("Set ended");
    }
    public void terminateGame() {
        gameState = State.Ended;
        terminatePoint();
    }
    public void terminatePoint() {
        pointState = State.Ended;
    }

    public boolean isMatchStarted() {
        return State.Started.equals(matchState);
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

    protected void checkMatchIsReady(){
        if(model.getPlayer1()==null || model.getPlayer2()==null) {
            matchState = State.Default;
            throw new RuntimeException("Game is not ready");
        }
    }

    public TennisGame(Game game) {
        this.model = game;
        this.gameScoreExecutor = new GameScoreExecutor(game.getGameScore());
        this.setScoreExecutor = new SetScoreExecutor(game.getSetScore());
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

    public AutoPlayer getMatchWinner() {
        return matchWinner;
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
            applySetScoreRules(player);
            terminateSet();
        }
    }

    public void applySetScoreRules(Player pr){
        AutoPlayer player = getPlayersMap().get(pr);
        int index = getPlayers().indexOf(player);
        setScoreExecutor.applyRules(index);
        if (setScoreExecutor.playerHasWon()){
            matchWinner = player;
            log.debug( player + " win the match" );
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