package de.spdmc.frodo.textparser;

import de.spdmc.frodo.data.Genres;
import de.spdmc.frodo.data.GermanGenre;
import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.textparser.InputContent;
import de.spdmc.frodo.textparser.Parser;

public class GenreParser extends Parser {

    public GenreParser() {
        super();
        //this.pattern = new String[]{"genre", "mag", "schaue"};
    }

    public GenreParser(String[] pattern) {
        super(pattern);
    }

    @Override
    public InputContent parse(String in) {
        return parse(in, false);
    }

    @Override
    public InputContent parse(String in, boolean answerQuestion) {
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
        if(inArr.length >= 1 && answerQuestion){
            if(inArr[0].contains("nein") || inArr[0].equals("nö") || inArr[0].equals("nä") || inArr[0].equals("ne")
                    || inArr[0].startsWith("nee") || inArr[0].contains("nöö") || inArr[0].contains("nää")){
                ic.setDialogState(Enumerations.DialogState.GENRE_DECLINED);
                return ic;
            }
        }
        for (int i = 0; i < Genres.genres.length; i++) {
            GermanGenre curr = Genres.genres[i];
            if (in.contains(curr.getName()) || in.contains(curr.getGermanName())) {
                ic.addData(curr.getName());
                ic.addData(Integer.toString(curr.getId()));
                ic.setDialogState(Enumerations.DialogState.GENRE_REPLY);
            }
        }
        if(ic.getData().isEmpty() && answerQuestion){
            if(inArr.length >= 1){
                if(inArr[0].equals("ja") || inArr[0].equals("jo") || inArr[0].contains("gern") || inArr[0].contains("natürlich")
                        || inArr[0].equals("jap") || inArr[0].equals("joa")){
                    ic.setDialogState(Enumerations.DialogState.GENRE_REASK);
                }
            }
        }
        return ic;
    }

}
