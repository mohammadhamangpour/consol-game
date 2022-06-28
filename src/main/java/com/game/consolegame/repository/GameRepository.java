package com.game.consolegame.repository;

import com.game.consolegame.domain.entity.GameEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GameRepository extends MongoRepository<GameEntity, String> {

    @Override
    Optional<GameEntity> findById(String s);
}
