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
        if (in.equals("ja") || in.equals("jo") || in.contains("gern") || in.contains("natürlich")
                || in.equals("jap") || in.equals("joa")) {
            if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_MOVIES_ASK_CONFIRM){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_ASK_MORE);
            } else if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_TVSHOW_ASK_CONFIRM){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_TVSHOW_ASK_MORE);
            } else if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_MOVIES_DECLINED ||
                    ic.getDialogState() == Enumerations.DialogState.FAVORITE_TVSHOW_DECLINED){
                ic.setDialogState(Enumerations.DialogState.RECOMMEND);
            }
        } else if (in.contains("nein") || in.equals("nö") || in.equals("nä") || in.equals("ne")
                || in.startsWith("nee") || in.contains("nöö") || in.contains("nää")) {
            if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_MOVIES_ASK_CONFIRM){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_REASK);
                ic.getData().clear();
            } else if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_TVSHOW_ASK_CONFIRM){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_TVSHOW_REASK);
                ic.getData().clear();
            } else if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_MOVIES_DECLINED){
                // noch nichts... was machen wenn user (noch) keine vorschlaege will?
            } else if(ic.getDialogState() == Enumerations.DialogState.FAVORITE_TVSHOW_DECLINED) {
                // noch nichts... was machen wenn user (noch) keine vorschlaege will?
            }
        }
        return ic;
    }
}
