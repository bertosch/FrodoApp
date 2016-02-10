package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;

public class MoreInfoParser extends Parser {

    @Override
    public InputContent parse(String in) {
        InputContent ic = new InputContent();
        if(in.contains("mehr") || in.contains("weitere") || in.contains("inhalt") || in.contains("beschreib") || in.startsWith("was")){
            ic.setDialogState(Enumerations.DialogState.MORE_INFO);
        }
        return ic;
    }

}
