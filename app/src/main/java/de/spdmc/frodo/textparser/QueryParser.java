package de.spdmc.frodo.textparser;

import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.textparser.InputContent;

public class QueryParser extends Parser {

    private String type = null;

    public QueryParser() {
        super();
        this.pattern = new String[]{"vorschlag", "schlag", "empfehle"};
    }

    public QueryParser(String[] pattern) {
        super(pattern);
    }

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");
        for (int j = 0; j < this.getPattern().length; j++) {
            String s1 = this.pattern[j];
            for (int i = 0; i < inArr.length; i++) {
                String s2 = inArr[i];
                if (type == null) {
                    if (s2.equals("film") || s2.equals("serie")) type = s2;
                }
                if (s2.startsWith(s1)) {
                    if (j == 1) {
                        for (int k = i + 1; k < inArr.length; k++) {
                            if (inArr[k].equals("vor")) {
                                System.out.println("schlag ... vor erkannt");
                                ic.setDialogState(Enumerations.DialogState.QUERY_REPLY);
                            }
                        }
                    } else {
                        System.out.println("DEBUG: " + s2 + " starts with " + s1);
                        ic.setDialogState(Enumerations.DialogState.QUERY_REPLY);
                    }
                }
            }
        }
        ic.addData(type);
        return ic;
    }

}
