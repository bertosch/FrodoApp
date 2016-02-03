package de.spdmc.frodo.textparser;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.results.ResultList;

import de.spdmc.frodo.Bot;
import de.spdmc.frodo.enumerations.Enumerations;

public class TvShowParser extends Parser {
    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");

        if (inArr.length == 1){ // nur ja oder nein als Antwort
            if(inArr[0].equals("ja")){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_TVSHOW_REASK);
                return ic;
            }
            else if(inArr[0].equals("nein")){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_TVSHOW_DECLINED);
                return ic;
            }
        }
        else if(inArr[0].equals("mein")){
            in = in.replaceFirst("mein ", "");
            String[] help = new String[inArr.length-1];
            for (int l = 0; l < help.length; l++) {
                help[l] = inArr[l+1];
            }
            inArr = help;
        }
        try {
            String s = in;
            TVBasic bestRated = new TVBasic();
            bestRated.setVoteAverage(0.0f);
            for (int i = inArr.length-1; i >= 0; i--){
                ResultList<TVBasic> result = Bot.tmdbSearch.searchTV(s, 0, null, null, null);
                if(result.isEmpty()){
                    s = "";
                    for(int j = 0; j < i; j++){
                        s += " " + inArr[j];
                    }
                    s = s.replaceFirst(" ", "");
                } else {
                    bestRated = result.getResults().get(0);
                    for(TVBasic t : result.getResults()) {
                        if (removeArticles(normalize(t.getName())).equals(removeArticles(s))) {
                            ic.addData(t.getName());
                            ic.addData(String.valueOf(t.getId()));
                            ic.setDialogState(Enumerations.DialogState.FAVORITE_TVSHOW_ASK_MORE);
                            return ic;
                        } else {
                            if(t.getVoteAverage() > bestRated.getVoteAverage()){
                                bestRated = t;
                            }
                        }
                    }
                }
            }
            if(ic.getData().isEmpty()){
                s = in;
                for (int i = 0; i < inArr.length; i++){
                    ResultList<TVBasic> result = Bot.tmdbSearch.searchTV(s, 0, null, null, null);
                    if(result.isEmpty()){
                        s = "";
                        for(int j = inArr.length-1; j > i; j--){
                            s = inArr[j] + " " + s;
                        }
                        s = s.substring(0,s.length()-1);
                    } else {
                        for(TVBasic t : result.getResults()) {
                            if (removeArticles(normalize(t.getName())).equals(removeArticles(s))) {
                                ic.addData(t.getName());
                                ic.addData(String.valueOf(t.getId()));
                                ic.setDialogState(Enumerations.DialogState.FAVORITE_TVSHOW_ASK_MORE);
                                return ic;
                            } else {
                                if(t.getVoteAverage() > bestRated.getVoteAverage()){
                                    bestRated = t;
                                }
                            }
                        }
                        ic.addData(bestRated.getName());
                        ic.addData(String.valueOf(bestRated.getId()));
                        ic.setDialogState(Enumerations.DialogState.FAVORITE_TVSHOW_ASK_CONFIRM);
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
