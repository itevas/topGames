package com.topgames.controller;

import com.topgames.dto.GameInfoDto;
import com.topgames.service.GameInfoService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/ios/games/charts")
public class TopGamesController {

    GameInfoService gameInfoService;

    @GetMapping("/{type}")
    ResponseEntity<List<GameInfoDto>> getTopFreeGamesList(@PathVariable String type,
                                                          @RequestParam Integer limit) {
        return ResponseEntity.ok(gameInfoService.getTopFreeGamesList(type, limit));
    }

}
