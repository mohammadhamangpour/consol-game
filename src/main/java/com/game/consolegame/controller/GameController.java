package com.game.consolegame.controller;


import com.game.consolegame.domain.entity.GameEntity;
import com.game.consolegame.exception.GameException;
import com.game.consolegame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {

    private GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping("/game/{bot}/{key}")
    public String game(@PathVariable("bot") String bot, @PathVariable("key") String key){
        return service.game(bot, key);
    }

    @GetMapping("/bots")
    public List<GameEntity> test(){
        return service.findAll();
    }
}
