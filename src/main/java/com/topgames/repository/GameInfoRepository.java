package com.topgames.repository;

import com.topgames.model.GameInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameInfoRepository extends CrudRepository<GameInfo, String> {

    Optional<GameInfo> findByGameId(String gameId);

    List<GameInfo> findByType(String type);

}
