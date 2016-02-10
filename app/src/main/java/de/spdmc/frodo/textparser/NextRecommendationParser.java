package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;

public class NextRecommendationParser extends Parser {

    @Override
    public InputContent parse(String in) {
        InputContent ic = new InputContent();
        if(((in.contains("schon") || in.contains("bereits")) && (in.contains("seh") || in.contains("kenn")))
                || ((in.contains("mag") || in.contains("zufrieden")
                || in.contains("glücklich") || in.contains("gluecklich")) && in.contains("nicht"))){
            ic.setDialogState(Enumerations.DialogState.NEXT_RECOMMENDATION);
        } else ic.setDialogState(Enumerations.DialogState.NEXT_RECOMMENDATION_FAULT);
        return ic;
    }

}
