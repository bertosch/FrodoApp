package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;

/**
 * Created by lars on 28.01.16.
 */
public class RecommendationParser extends Parser {

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        if(in.equals("gib mir eine empfehlung")){
            ic.setDialogState(Enumerations.DialogState.RECOMMEND);
        }
        return ic;
    }

}
