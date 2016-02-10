package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;

public class NextRecommendationParser extends Parser {

    @Override
    public InputContent parse(String in) {
        InputContent ic = new InputContent();
        if(((in.contains("schon") || in.contains("bereits")) && (in.contains("sehen") || in.contains("kenne")))
                || (in.contains("mag") && in.contains("nicht"))){
            ic.setDialogState(Enumerations.DialogState.NEXT_RECOMMENDATION);
        } else ic.setDialogState(Enumerations.DialogState.NEXT_RECOMMENDATION_FAULT);
        return ic;
    }

}
