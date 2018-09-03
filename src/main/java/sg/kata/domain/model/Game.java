package sg.kata.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sg.kata.domain.score.GameScoreExecutor;
import sg.kata.domain.score.SetScoreExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nassim MOUALEK on 31/08/2018.
 */
@Component
@Slf4j
@ToString
public class Game implements IGame {
    @Autowired
    private ObjectMapper objectMapper;
    private final Map<String, Player> players = new HashMap<>();

    private GameScoreExecutor gameScoreExecutor = new GameScoreExecutor(this);
    private SetScoreExecutor setScoreExecutor = new SetScoreExecutor(this);


    @Getter
    private Player pointWinner;
    @Getter
    private Player gameWinner;
    @Getter
    private Player setWinner;

    private void clearWinners() {
        pointWinner = null;
        gameWinner = null;
        setWinner = null;
    }


    @Override
    public String serialize() {
        Map<String, Object> me = new HashMap<>();
        me.put("game", this);
        me.put("players", players);
        try {
            return objectMapper.writeValueAsString(me);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void join(Player player) {
        if (players.size()<2)
            players.put(player.getName(), player);
        else
            throw new RuntimeException("Game is full");
    }

    public String getOpponentName(String name) {
        for (String playerName : players.keySet().toArray(new String[0])) {
            if (!playerName.equals(name))
                return playerName;
        }
        return null;
    }

    public void winThePoint(String name) {
        clearWinners();
        pointWinner = getPlayer(name);
        gameScoreExecutor.applyRules(name);
    }

    public void winTheGame(String name) {
        clearWinners();
        gameWinner = getPlayer(name);
        setScoreExecutor.applyRules(name);
        resetGameScores();
    }

    public void winTheSet(String name) {
        clearWinners();
        setWinner = getPlayer(name);
    }

    public void reset() {
        players.clear();
    }

    public void resetGameScores() {
        for(Player p: players.values())
            if (p!=null)
                p.resetGameScore();
    }

    public void resetSetScores() {
        for(Player p: players.values())
            p.resetSetScore();
    }

    public String getPlayerName(String name) {
        return getPlayer(name).getName();
    }
    private Player getPlayer(String name) {
        Player player = players.get(name);
        if (player==null)
            throw new RuntimeException("Player not founds");
        return player;
    }
    public int getPlayerGameScore(String name) {
        return getPlayer(name).getGameScore().getScore();
    }

    public int getPlayerSetScore(String name) {
        return getPlayer(name).getSetScore().getScore();
    }

    public boolean isPlayerDeuce(String name) {
        return getPlayer(name).getGameScore().isDeuce();
    }

    public void setPlayerDeuce(String name, boolean deuce) {
        getPlayer(name).getGameScore().setDeuce(deuce);
    }

    public void setPlayerGameScore(String name, int score) {
        getPlayer(name).getGameScore().setScore(score);
    }

    public void setPlayerSetScore(String name, int score) {
        getPlayer(name).getSetScore().setScore(score);
    }

}