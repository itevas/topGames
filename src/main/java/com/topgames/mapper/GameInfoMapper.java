package com.topgames.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.topgames.dto.GameInfoDto;
import com.topgames.model.GameInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameInfoMapper {

    static ModelMapper mapper = new ModelMapper();

    public GameInfoDto toGameInfoDto(GameInfo gameInfo) {
        return mapper.map(gameInfo, GameInfoDto.class);
    }

    public List<GameInfo> toGameInfoList(JsonNode jsonNode) {
        List<GameInfo> gameInfoList = new ArrayList<>();
        JsonNode jsonArray = jsonNode.get("feed").get("results");
        if (jsonArray.isArray()) {
            int i = 1;
            for (JsonNode jsonObject : jsonArray) {
                gameInfoList.add(toGameInfo(jsonObject, i++));
            }
        }
        return gameInfoList;
    }

    GameInfo toGameInfo(JsonNode jsonObject, int top) {
        return GameInfo.builder()
                .top(top)
                .gameId(jsonObject.get("id").asText())
                .artistName(jsonObject.get("artistName").asText())
                .name(jsonObject.get("name").asText())
                .releaseDate(jsonObject.get("releaseDate").asText())
                .url(jsonObject.get("url").asText())
                .build();
    }

}
