package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.textparser.GreetingsParser;
import de.spdmc.frodo.textparser.InputContent;

public class NameParser extends Parser {

    public NameParser() {
        super();
        this.pattern = new String[]{"heiße", "heisse", "name", "bin"};
    }

    public NameParser(String[] pattern) {
        super(pattern);
    }

    @Override
    public InputContent parse(String in, boolean answerQuestion) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");
        GreetingsParser gp = new GreetingsParser();
        if ((inArr[0].contains("nein") || inArr[0].equals("nö") || inArr[0].equals("nä") || inArr[0].equals("ne")
                || inArr[0].startsWith("nee") || inArr[0].contains("nöö") || inArr[0].contains("nää") || in.contains("nicht")) && answerQuestion) {
            ic.setDialogState(Enumerations.DialogState.NAME_DECLINED);
            return ic;
        } else if(inArr[0].equals("ja") || inArr[0].equals("jo") || inArr[0].contains("gern") || inArr[0].contains("natürlich")
                || inArr[0].equals("jap") || inArr[0].equals("joa") && answerQuestion){
            ic.setDialogState(Enumerations.DialogState.NAME_REASK2);
            return ic;
        } else if ((inArr.length == 1 || inArr.length == 2) && answerQuestion) {
            for(String s : gp.getPattern()){
                if(in.startsWith(s)) {
                    ic.setDialogState(Enumerations.DialogState.GREETING_REPLY);
                    return ic;
                }
            }
            ic.setDialogState(Enumerations.DialogState.NAME_REPLY);
            String h = Character.toUpperCase(inArr[0].charAt(0)) + inArr[0].substring(1);
            ic.addData(h);
            return ic;
        } else {
            for (String s1 : this.getPattern()) {
                for (int i = 0; i < inArr.length; i++) {
                    String s2 = inArr[i];
                    if (s2.contains(s1)) {
                        if (s2.contains(this.pattern[0])
                                || s2.contains(this.pattern[1])
                                || s2.contains(this.pattern[3])) {
                            if (inArr.length >= i + 2) {
                                ic.setDialogState(Enumerations.DialogState.NAME_REPLY);
                                String h = Character.toUpperCase(inArr[i + 1].charAt(0)) + inArr[i + 1].substring(1);
                                ic.addData(h);
                                return ic;
                            }
                        } else if (s2.contains(this.pattern[2])) {
                            if (inArr.length >= i + 3) {
                                ic.setDialogState(Enumerations.DialogState.NAME_REPLY);
                                String h = Character.toUpperCase(inArr[i + 2].charAt(0)) + inArr[i + 2].substring(1);
                                ic.addData(h);
                                return ic;
                            }
                        }
                    }
                }
            }
        }
        return ic;
    }

    @Override
    public InputContent parse(String in) {
        return parse(in, false);
    }

}
