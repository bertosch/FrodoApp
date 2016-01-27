package de.spdmc.frodo.textparser;

import de.spdmc.frodo.data.Genres;
import de.spdmc.frodo.data.GermanGenre;
import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.textparser.InputContent;
import de.spdmc.frodo.textparser.Parser;

public class GenreParser extends Parser {

    public GenreParser() {
        super();
        this.pattern = new String[]{"genre", "mag", "schaue"};
    }

    public GenreParser(String[] pattern) {
        super(pattern);
    }

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");
        // Satzbaubasierte Suche zunaechst auskommentiert
        /*
        if(inArr.length == 1){
            for(int i = 0; i < Genres.genres.length; i++){
                GermanGenre curr = Genres.genres[i];
                if(inArr[0].contains(curr.getName()) || inArr[0].contains(curr.getGermanName())) {
                    ic.addData(curr.getName());
                    ic.addData(Integer.toString(curr.getId()));
                    ic.setDialogState(Enumerations.DialogState.GENRE_REPLY);
                }
            }
        }
        // TODO nur [GENRE] als Antwort erkennen

        for(int i = 0; i < pattern.length; i++){
            String s1 = pattern[i];
            for(int j = 0; j < inArr.length; j++){
                String s2 = inArr[j];
                if(s2.contains(s1)){
                    if(i == 0 && inArr.length > j+2){ // *genre ist [GENRE]
                        if(inArr[j+1].equals("ist") || inArr[j+1].equals("lautet")) ic.addData(inArr[j+2]);
                        ic.setDialogState(Enumerations.DialogState.GENRE_REPLY);
                        return ic;
                    }
                    else if(i == 1){ // mag
                        if(inArr.length > j+2){ // mag gern(e) [GENRE]
                            if(inArr[j+1].contains("gern")) ic.addData(inArr[j+2]);
                        }
                        // TODO mag (extraordinaer besonders super) gerne [GENRE] erkennen
                        else if(inArr.length > j+1){ // mag [GENRE]
                            ic.addData(inArr[j+1]);
                        }
                        ic.setDialogState(Enumerations.DialogState.GENRE_REPLY);
                        return ic;
                    }
                    else if(i == 2) { // schaue gern(e) [GENRE]
                        if (inArr.length > j+2) {
                            if (inArr[j+1].contains("gern")) ic.addData(inArr[j+2]);
                            ic.setDialogState(Enumerations.DialogState.GENRE_REPLY);
                            return ic;
                        }
                        // TODO schaue (extraordinaer besonders super) gerne [GENRE] erkennen
                    }
                }
            }
        }*/
        for(int j = 0; j < inArr.length; j++) {
            String s = inArr[j];
            for (int i = 0; i < Genres.genres.length; i++) {
                GermanGenre curr = Genres.genres[i];
                if (in.contains(curr.getName()) || in.contains(curr.getGermanName())) {
                    ic.addData(curr.getName());
                    ic.addData(Integer.toString(curr.getId()));
                    ic.setDialogState(Enumerations.DialogState.GENRE_REPLY);
                }
            }
        }
        if(ic.getDialogState() == null) ic.setDialogState(Enumerations.DialogState.GENRE_FAULT_REPLY);
        return ic;
    }

}
