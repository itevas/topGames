package com.topgames.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.topgames.mapper.GameInfoMapper;
import com.topgames.model.GameInfo;
import com.topgames.repository.GameInfoRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateService {

    GameInfoMapper gameInfoMapper;
    GameInfoRepository gameInfoRepository;

    static final String TIME_TO_UPDATE = "00 00 * * * *";
    static final String FREE_GAMES_URL =
            "https://rss.itunes.apple.com/api/v1/ua/ios-apps/top-free/games/100/explicit.json";
    static final String PAID_GAMES_URL =
            "https://rss.itunes.apple.com/api/v1/ua/ios-apps/top-paid/games/100/explicit.json";
    static final String GROSSING_GAMES_URL =
            "https://rss.itunes.apple.com/api/v1/ua/ios-apps/top-grossing/all/100/explicit.json";
    static final String FREE = "free";
    static final String PAID = "paid";
    static final String GROSSING = "grossing";


    @Async
    @Scheduled(cron = TIME_TO_UPDATE)
    void startUpdating() {
        updateGamesList(FREE_GAMES_URL, FREE);
        updateGamesList(PAID_GAMES_URL, PAID);
        updateGamesList(GROSSING_GAMES_URL, GROSSING);
    }

    void updateGamesList(String url, String type) {
        WebClient webClient = WebClient
                .create(url);
        JsonNode response = webClient
                .get()
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        update(response, type);
    }

    void update(JsonNode jsonNode, String type) {
        List<GameInfo> gameInfoList = gameInfoMapper.toGameInfoList(jsonNode);
        gameInfoList.forEach(gameInfo -> {
            if (gameInfoRepository.findByGameId(gameInfo.getGameId()).isPresent()
                    && gameInfoRepository.findByGameId(gameInfo.getGameId()).get().getType().equals(type)) {
                gameInfo.setId(gameInfoRepository.findByGameId(gameInfo.getGameId()).get().getId());
            }
            gameInfo.setType(type);
            gameInfoRepository.save(gameInfo);
        });
        log.info(type + " games updated!");
    }

}
