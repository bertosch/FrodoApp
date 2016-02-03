package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;

public class RecommendationParser extends Parser {

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        if(in.contains("empfiehl") || in.contains("empfehl")){
            ic.setDialogState(Enumerations.DialogState.RECOMMEND);
        }
        return ic;
    }

}
