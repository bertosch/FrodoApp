package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;

public class NextRecommendationParser extends Parser {

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        String[] inArr = in.split(" ");
        InputContent ic = new InputContent();
        if(inArr[0].equals("nein") || inArr[0].equals("nö") || inArr[0].equals("nä") || inArr[0].equals("ne")
                || inArr[0].startsWith("nee") || inArr[0].contains("nöö") || inArr[0].contains("nää") || in.contains("kenn")
                || (in.contains("schon") || in.contains("bereits")) && (in.contains("seh"))
                || ((in.contains("mag") || in.contains("zufrieden") || in.contains("weniger")
                || in.contains("glücklich") || in.contains("gluecklich") || in.contains("geschmack")
                || in.contains("gut") || in.contains("gefällt") || in.contains("gefaellt") || in.contains("auch"))
                && in.contains("nicht"))){
            ic.setDialogState(Enumerations.DialogState.NEXT_RECOMMENDATION);
        } //else ic.setDialogState(Enumerations.DialogState.NEXT_RECOMMENDATION_FAULT);
        return ic;
    }

}
