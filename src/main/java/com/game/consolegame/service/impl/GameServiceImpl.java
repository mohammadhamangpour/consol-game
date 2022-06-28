package com.game.consolegame.service.impl;

import com.game.consolegame.exception.GameException;
import com.game.consolegame.repository.GameRepository;
import com.game.consolegame.domain.dto.CoordinatesDto;
import com.game.consolegame.domain.entity.GameEntity;
import com.game.consolegame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    private GameRepository repository;

    @Autowired
    public GameServiceImpl(GameRepository repository) {
        this.repository = repository;
    }

    @Override
    public String game(String bot, String key) {
        makeBotIfNotExist();
        String result = game(action(bot, key));
        System.out.println(result);
        return result;
    }

    private List<CoordinatesDto> action(String bot, String key) {
        Optional<GameEntity> botDetail = repository.findById(bot);
        if (botDetail.isPresent()) {
            GameEntity entity = botDetail.get();
            switch (key.toLowerCase()) {
                case "w":
                    entity.setX(entity.getX() - 1);
                    repository.save(condition(entity));
                    break;
                case "a":
                    entity.setY(entity.getY() - 1);
                    repository.save(condition(entity));
                    break;
                case "d":
                    entity.setY(entity.getY() + 1);
                    repository.save(condition(entity));
                    break;
                case "s":
                    entity.setX(entity.getX() + 1);
                    repository.save(condition(entity));
                    break;
                default:
                    throw new RuntimeException("i don't know why");
            }
            List<CoordinatesDto> coordinates = new ArrayList<>();
            repository.findAll().forEach(gameEntity -> coordinates.add(new CoordinatesDto(gameEntity.getX(), gameEntity.getY())));
            return coordinates;
        } else {
            List<CoordinatesDto> coordinates = new ArrayList<>();
            repository.findAll().forEach(gameEntity -> coordinates.add(new CoordinatesDto(gameEntity.getX(), gameEntity.getY())));
            return coordinates;
        }
    }

    private GameEntity condition(GameEntity entity) {
        GameEntity bot1 = repository.findById("bot1").get();
        GameEntity bot2 = repository.findById("bot2").get();
        switch (entity.getId()) {
            case "bot1":
                if ((entity.getX() >= 1 && entity.getX() <= 9) && (entity.getY() >= 1 && entity.getY() <= 9)
                        && (entity.getY() != bot2.getY() || entity.getX() != bot2.getX())) {
                    return entity;
                } else {
                    throw new GameException("not match condition", 400, HttpStatus.BAD_REQUEST);
                }

            case "bot2":
                if ((entity.getX() >= 1 && entity.getX() <= 9) && (entity.getY() >= 1 && entity.getY() <= 9)
                        && (entity.getY() != bot1.getY() || entity.getX() != bot1.getX())) {
                    return entity;
                } else {
                    throw new GameException("not match condition", 400, HttpStatus.BAD_REQUEST);
                }
            default:
                throw new GameException("not match condition", 400, HttpStatus.BAD_REQUEST);
        }
    }

    public String game(List<CoordinatesDto> bots) {
        StringBuilder builder = new StringBuilder();
        String[][] strings = new String[11][11];
        for (int x = 0; x <= 10; x++) {
            for (int y = 0; y <= 10; y++) {
                if (x == 0 || x == 10 || y == 0 || y == 10) {
                    strings[x][y] = "*";
                    builder.append(strings[x][y]);
                } else {
                    CoordinatesDto bot1 = bots.get(0);
                    CoordinatesDto bot2 = bots.get(1);
                    if (x == bot1.getX() && y == bot1.getY()) {
                        strings[x][y] = "⊕";
                        builder.append(strings[x][y]);
                    } else if (x == bot2.getX() && y == bot2.getY()) {
                        strings[x][y] = "◘";
                        builder.append(strings[x][y]);
                    } else {
                        strings[x][y] = " ";
                        builder.append(strings[x][y]);
                    }
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private void makeBotIfNotExist() {
        if (!repository.existsById("bot1")) {
            repository.save(new GameEntity("bot1", 5, 3));
        }

        if (!repository.existsById("bot2")) {
            repository.save(new GameEntity("bot2", 5, 7));
        }
    }

    @Override
    public List<GameEntity> findAll() {
        return repository.findAll();
    }
}
