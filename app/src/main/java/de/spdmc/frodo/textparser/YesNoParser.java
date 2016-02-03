package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.profile.Profile;

public class YesNoParser extends Parser {

    InputContent ic;
    Profile p;

    public YesNoParser(InputContent ic, Profile p){
        super();
        this.ic = ic;
        this.p = p;
    }

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        if(in.contains("ja")){
            if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_MOVIES_ASK_CONFIRM){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_ASK_MORE);
            } else if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_TVSHOW_ASK_CONFIRM){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_TVSHOW_ASK_MORE);
            } else if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_MOVIES_DECLINED ||
                    ic.getDialogState() == Enumerations.DialogState.FAVORITE_TVSHOW_DECLINED){
                ic.setDialogState(Enumerations.DialogState.RECOMMEND);
            }
        } else if(in.contains("nein")){
            if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_MOVIES_ASK_CONFIRM){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_REASK);
                ic.getData().clear();
            } else if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_TVSHOW_ASK_CONFIRM){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_TVSHOW_REASK);
                ic.getData().clear();
            } else if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_MOVIES_DECLINED){
                if(p.getFavorite_series().isEmpty()) ic.setDialogState(Enumerations.DialogState.FAVORITE_TVSHOW_IN_ADDITION);
            } else if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_TVSHOW_DECLINED) {
                if (p.getFavorite_movies().isEmpty())
                    ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_IN_ADDITION);
            }
        }
        return ic;
    }
}
