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
        FAVORITE_TYPE_REASK,
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
        GENRE_REASK,
        FAVORITE_MOVIES_REASK,
        FAVORITE_MOVIES_DECLINED,
        FAVORITE_MOVIES_ASK_MORE,
        FAVORITE_ACTOR_FAULT_REPLY,
        FAVORITE_MOVIES_ASK_CONFIRM,
        PARSE_FAVORITE_MOVIE_YES_NO,
        FAVORITE_TVSHOW_REASK,
        FAVORITE_TVSHOW_DECLINED,
        FAVORITE_TVSHOW_ASK_MORE,
        FAVORITE_TVSHOW_ASK_CONFIRM,
        PARSE_FAVORITE_TVSHOW,
        PARSE_FAVORITE_TVSHOW_YES_NO,
        PARSE_RECOMMENDATION_YES_NO,
        GOODBYE,
        FAVORITE_MOVIES_REASK_FAULT,
        FAVORITE_TVSHOW_REASK_FAULT,
        QUESTION_REPLY_NAME,
        QUESTION_REPLY_NAME_FAULT,
        QUESTION_REPLY_FAV_ACTOR,
        QUESTION_REPLY_FAV_ACTOR_FAULT,
        QUESTION_REPLY_FAV_GENRE,
        QUESTION_REPLY_FAV_GENRE_FAULT,
        QUESTION_REPLY_FAV_MOVIES,
        QUESTION_REPLY_FAV_MOVIES_FAULT,
        QUESTION_REPLY_FAV_MOVIES_WRONG_TYPE,
        QUESTION_REPLY_NO_TYPE,
        QUESTION_REPLY_FAV_SERIES,
        QUESTION_REPLY_FAV_SERIES_FAULT,
        QUESTION_REPLY_FAV_SERIES_WRONG_TYPE,
        PARSE_FAVORITE_MOVIE
    }

}
