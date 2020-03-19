package com.topgames.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("GameInfo")
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameInfo implements Serializable {

    @Id
    String id;

    @Indexed
    String gameId;

    @Indexed
    Integer top;

    @Indexed
    String type;

    String name;
    String url;
    String releaseDate;
    String artistName;

}
