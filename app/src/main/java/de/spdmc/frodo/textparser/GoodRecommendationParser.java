package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;

public class GoodRecommendationParser extends Parser {
    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        String[] inArr = in.split(" ");
        InputContent ic = new InputContent();

        if(inArr[0].equals("ja") || inArr[0].equals("jo") || inArr[0].contains("gern") || inArr[0].contains("natürlich")
                || inArr[0].equals("jap") || inArr[0].equals("joa") || in.contains("danke") || in.contains("gut") || in.contains("gefällt") || in.contains("gefaellt")){
            ic.setDialogState(Enumerations.DialogState.GOODBYE);
        }

        return ic;
    }
}
