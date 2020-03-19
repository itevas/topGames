package com.topgames.service;

import com.topgames.dto.GameInfoDto;
import com.topgames.mapper.GameInfoMapper;
import com.topgames.model.GameInfo;
import com.topgames.repository.GameInfoRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameInfoService {

    GameInfoRepository gameInfoRepository;
    GameInfoMapper gameInfoMapper;

    public List<GameInfoDto> getTopFreeGamesList(String type, Integer limit) {
        if (limit > gameInfoRepository.findByType(type).size()) {
            limit = gameInfoRepository.findByType(type).size();
        }
        return gameInfoRepository.findByType(type)
                .stream()
                .sorted(Comparator.comparingInt(GameInfo::getTop))
                .collect(Collectors.toList())
                .subList(0, limit)
                .stream()
                .map(gameInfo -> gameInfoMapper.toGameInfoDto(gameInfo))
                .collect(Collectors.toList());
    }

}
