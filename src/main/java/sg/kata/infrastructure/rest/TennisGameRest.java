package sg.kata.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sg.kata.infrastructure.services.TennisGameService;

@RestController
public class TennisGameRest {

    @Autowired
    TennisGameService gameService;

    @GetMapping(value = "/winThePoint/{player}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String winThePoint(@PathVariable String player) {
        gameService.winThePoint(player);
        return gameService.getGame().serialize();
    }

    @GetMapping(value = "/joinTheGame/{player}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String joinTheGame(@PathVariable String player) {
        gameService.joinTheGame(player);
        return gameService.getGame().serialize();
    }

}
