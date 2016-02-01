package de.spdmc.frodo.enumerations;

public class Enumerations {

    public enum DialogState {
        PARSE_GREETING,
        PARSE_QUERY,
        PARSE_NAME,
        NAME_REPLY,
        NAME_DECLINED,
        NAME_REASK,
        NAME_REASK2,
        QUERY_REPLY,
        NAME_WITHDRAW, // Namen widerrufen
        PARSE_FAVORITE_TYPE,
        FAVORITE_TYPE_REPLY,
        PARSE_GENRE,
        GREETING_REPLY,
        GENRE_REPLY,
        GENRE_FAULT_REPLY,
        PARSE_FAVORITE_ACTOR,
        FAVORITE_TYPE_FAULT_REPLY,
        FAVORITE_ACTOR_DECLINED,
        FAVORITE_ACTOR_REASK,
        FAVORITE_ACTOR_REPLY,
        RECOMMEND,
        GENRE_DECLINED,
        GENRE_REASK;
    }

}
