package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.textparser.InputContent;

public class FavoriteTypeParser extends Parser {

    public FavoriteTypeParser() {
        super();
        this.pattern = new String[]{"film", "serie"};
    }

    public FavoriteTypeParser(String[] pattern) {
        super(pattern);
    }

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");
        for (String s1 : this.pattern) {
            for (String s2 : inArr) {
                if (s2.contains(s1)) {
                    ic.addData(s1);
                    ic.setDialogState(Enumerations.DialogState.FAVORITE_TYPE_REPLY);
                }
            }
        }
        if(ic.getDialogState() == null) ic.setDialogState(Enumerations.DialogState.FAVORITE_TYPE_FAULT_REPLY);
        return ic;
    }

}
