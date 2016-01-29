package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.textparser.InputContent;
import de.spdmc.frodo.textparser.Parser;

public class GreetingsParser extends Parser {

    public GreetingsParser() {
        super();
        this.pattern = new String[]{"hallo", "guten tag", "hi"};
    }

    public GreetingsParser(String[] pattern) {
        super(pattern);
    }

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");
        for (String s1 : this.getPattern()) {
            for (String s2 : inArr) {
                if (s2.contains(s1)) ic.setDialogState(Enumerations.DialogState.GREETING_REPLY);
            }
        }
        return ic;
    }

}
