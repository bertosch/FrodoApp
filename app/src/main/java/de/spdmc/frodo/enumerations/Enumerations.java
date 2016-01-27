package de.spdmc.frodo.enumerations;

public class Enumerations {

    public enum DialogState {
        PARSE_GREETING,
        PARSE_QUERY,
        PARSE_NAME,
        NAME_REPLY,
        NAME_DECLINED,
        NAME_REASK,
        QUERY_REPLY,
        NAME_WITHDRAW, // Namen widerrufen
        PARSE_FAVORITE_TYPE,
        FAVORITE_TYPE_REPLY,
        PARSE_GENRE,
        GREETING_REPLY,
        GENRE_REPLY,
        GENRE_FAULT_REPLY,
        FAVORITE_TYPE_FAULT_REPLY
    }

}
