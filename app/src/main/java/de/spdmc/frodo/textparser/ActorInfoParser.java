package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;

public class ActorInfoParser extends Parser {

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        if(in.contains("schauspieler") || (in.contains("wer") && in.contains("spielt"))){
            ic.setDialogState(Enumerations.DialogState.WHICH_ACTORS);
        }
        return ic;
    }

}
