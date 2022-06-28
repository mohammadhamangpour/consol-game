package com.game.consolegame.service;

import com.game.consolegame.domain.entity.GameEntity;

import java.util.List;

public interface GameService {

    String game(String bot, String key);
    List<GameEntity> findAll();
}
