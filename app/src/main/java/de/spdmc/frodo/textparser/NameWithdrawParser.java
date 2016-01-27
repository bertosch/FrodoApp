package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.profile.Profile;
import de.spdmc.frodo.textparser.InputContent;
import de.spdmc.frodo.textparser.Parser;

public class NameWithdrawParser extends Parser {

    private boolean containsName = false; // true wenn input das wort 'name' beinhaltet
    private boolean containsPatternEl = false; // true wenn input element aus pattern beinhaltet (negatoren)
    private Profile p;

    public NameWithdrawParser(Profile p) {
        super();
        this.p = p;
        this.pattern = new String[]{"nicht", "falsch"};
    }

    public NameWithdrawParser(String[] pattern, Profile p) {
        super(pattern);
        this.p = p;
    }

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");
        for (String s1 : this.pattern) {
            for (String s2 : inArr) {
                if (s2.contains("name")) containsName = true;
                else if(s2.contains(p.getName().toLowerCase())) containsName = true;
                if (s2.contains(s1)) containsPatternEl = true;
            }
        }
        if (containsName && containsPatternEl) ic.setDialogState(Enumerations.DialogState.NAME_WITHDRAW);
        return ic;
    }

}
