package de.spdmc.frodo.textparser;

import de.spdmc.frodo.Bot;
import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.profile.Profile;

public class UseProfileParser extends Parser {

    private Profile p;

    public UseProfileParser(Profile p) {
        this.p = p;
    }

    @Override
    public InputContent parse(String in) {
        InputContent ic = new InputContent();
        in = normalize(in);
        String[] inArr = in.split(" ");
        if(inArr[0].equals("ja") || inArr[0].equals("jo") || in.contains("gern") || in.contains("natürlich")
                || inArr[0].equals("jap") || inArr[0].equals("joa")){
            if(p.getFavorite_type() != null) {
                if (p.getFavorite_type().equals("serie")) {
                    ic.setDialogState(Enumerations.DialogState.FAVORITE_TVSHOW_REASK);
                } else {
                    ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_REASK);
                }
            }
            else ic.setDialogState(Enumerations.DialogState.FAVORITE_TYPE_REASK);
        }
        else if(in.contains("nein") || inArr[0].equals("nö") || inArr[0].equals("nä") || inArr[0].equals("ne")
                || in.startsWith("nee") || in.contains("nöö") || in.contains("nää")){
            ic.setDialogState(Enumerations.DialogState.PARSE_NAME);
            Bot.setProfile(new Profile());
            Bot.resetSavedReply();
        }
        return ic;
    }

}
