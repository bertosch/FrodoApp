package de.spdmc.frodo.textparser;

import de.spdmc.frodo.Bot;
import de.spdmc.frodo.data.Genres;
import de.spdmc.frodo.data.GermanGenre;
import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.profile.Profile;

public class QuestionParser extends Parser {

    Profile p;

    public QuestionParser(Profile p){
        super();
        this.p = p;
    }

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");

        if(((in.contains("heiss") || in.contains("heiß")) && in.contains("ich"))
                || (in.contains("mein") && in.contains("name"))){
            if(p.getName() != null) {
                ic.addData(p.getName());
                ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_NAME);
            } else ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_NAME_FAULT);
        } else if (in.contains("schauspieler") && (in.contains("ich") || in.contains("mein"))){
            if(p.getFavorite_actor() != null){
                ic.addData(p.getFavorite_actor());
                ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_FAV_ACTOR);
            } else ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_FAV_ACTOR_FAULT);
        } else if (in.contains("genre") && (in.contains("ich") || in.contains("mein"))){
            if(p.getFavorite_genre() != null){
                ic.addData(p.getFavorite_genre());
                ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_FAV_GENRE);
            } else ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_FAV_GENRE_FAULT);
        } else if (in.contains("film") && (in.contains("ich") || in.contains("mein"))){
            if(p.getFavorite_type() != null) {
                if (p.getFavorite_type().equals("film")) {
                    if (!p.getFavorite_movies().isEmpty()) {
                        for (String name : p.getFavorite_movies()) {
                            ic.addData(name);
                        }
                        ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_FAV_MOVIES);
                    } else
                        ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_FAV_MOVIES_FAULT);
                } else
                    ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_FAV_MOVIES_WRONG_TYPE);
            } else ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_NO_TYPE);
        } else if (in.contains("serie") && (in.contains("ich") || in.contains("mein"))){
            if(p.getFavorite_type() != null) {
                if (p.getFavorite_type().equals("serie")) {
                    if (!p.getFavorite_series().isEmpty()) {
                        for (String name : p.getFavorite_series()) {
                            ic.addData(name);
                        }
                        ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_FAV_SERIES);
                    } else
                        ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_FAV_SERIES_FAULT);
                } else
                    ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_FAV_SERIES_WRONG_TYPE);
            } else ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_NO_TYPE);
        } else if(Bot.getCurrentState() == Enumerations.DialogState.PARSE_GENRE){
            if((in.contains("welche") || in.contains("was für") || in.contains("was fuer"))
                    && (in.contains("genre") || in.contains("möglichkeit") || in.contains("moeglichkeit"))){
                for (GermanGenre genre : Genres.genres){;
                    ic.addData(genre.getName());
                    ic.setDialogState(Enumerations.DialogState.QUESTION_REPLY_GENRE_LIST);
                }
            }
        }

        return ic;
    }

}
