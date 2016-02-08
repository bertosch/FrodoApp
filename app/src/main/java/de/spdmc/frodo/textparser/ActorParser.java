package de.spdmc.frodo.textparser;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.results.ResultList;

import de.spdmc.frodo.Bot;
import de.spdmc.frodo.enumerations.Enumerations;

public class ActorParser extends Parser {

    public ActorParser(){
        super();
    }

    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");
        if (inArr.length == 1){ // nur ja oder nein als Antwort
            if(inArr[0].equals("ja") || inArr[0].equals("jo") || inArr[0].contains("gern") || inArr[0].contains("natürlich")
                    || inArr[0].equals("jap") || inArr[0].equals("joa")){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_ACTOR_REASK);
                return ic;
            }
        }
        if(inArr[0].contains("nein") || inArr[0].equals("nö") || inArr[0].equals("nä") || inArr[0].equals("ne")
                || inArr[0].startsWith("nee") || inArr[0].contains("nöö") || inArr[0].contains("nää")){
            ic.setDialogState(Enumerations.DialogState.FAVORITE_ACTOR_DECLINED);
            return ic;
        }
        try {
            String s = inArr[inArr.length-1];
            for (int i = inArr.length-1; (i >= inArr.length-3) && (i >= 0); i--){
                ResultList<PersonFind> result = Bot.tmdbSearch.searchPeople(s, 0, false, null);
                if(result.isEmpty()){
                    if(i > 0) s = inArr[i-1] + " " + s;
                } else if (!normalize(result.getResults().get(0).getName())
                        .equals(s)){ // erstes suchresultat matched suchname nicht
                    if(i > 0) s = inArr[i-1] + " " + s;
                } else {
                    ic.addData(result.getResults().get(0).getName());
                    ic.addData(String.valueOf(result.getResults().get(0).getId()));
                    ic.setDialogState(Enumerations.DialogState.FAVORITE_ACTOR_REPLY);
                    return ic;
                }
            }
            if(ic.getData().isEmpty()){
                s = inArr[0];
                for (int i = 0; (i <= 1) && (i < inArr.length-1); i++){
                    ResultList<PersonFind> result = Bot.tmdbSearch.searchPeople(s, 0, false, null);
                    if(result.isEmpty()){
                        s += " " + inArr[i+1];
                    } else if (!normalize(result.getResults().get(0).getName())
                            .equals(s)){ // erstes suchresultat matched suchname nicht
                        s += " " + inArr[i+1];
                    } else {
                        ic.addData(result.getResults().get(0).getName());
                        ic.addData(String.valueOf(result.getResults().get(0).getId()));
                        ic.setDialogState(Enumerations.DialogState.FAVORITE_ACTOR_REPLY);
                        return ic;
                    }
                }
            }
        }
        catch (MovieDbException me){
            me.printStackTrace();
        }

        return ic;
    }

}
