package de.spdmc.frodo.textparser;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.results.ResultList;

import de.spdmc.frodo.Bot;
import de.spdmc.frodo.enumerations.Enumerations;

public class MoviesParser extends Parser {
    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");

        if (inArr.length == 1){ // nur ja oder nein als Antwort
            if(inArr[0].equals("ja") || inArr[0].equals("jo") || inArr[0].contains("gern")
                    || inArr[0].contains("natürlich") || inArr[0].equals("jap") || inArr[0].equals("joa") || inArr[0].contains("genau")){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_REASK);
                return ic;
            }
        }
        else {
            if (inArr[0].equals("mein")) {
                in = in.replaceFirst("mein ", "");
                String[] help = new String[inArr.length - 1];
                System.arraycopy(inArr, 1, help, 0, help.length);
                inArr = help;
            }
        }
        if(inArr[0].contains("nein") || inArr[0].equals("nö") || inArr[0].equals("nä")
                || inArr[0].equals("ne") || inArr[0].startsWith("nee") || inArr[0].contains("nöö") || inArr[0].contains("nää") || in.contains("keine weiteren") || in.contains("sonst keine")){
            ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_DECLINED);
            return ic;
        }
        try {
            String s = in;
            MovieInfo bestRated = new MovieInfo();
            bestRated.setVoteAverage(0.0f);
            for (int i = inArr.length-1; i >= 0; i--){
                // TODO de als language
                ResultList<MovieInfo> result = Bot.tmdbSearch.searchMovie(s, 0, null, false, null, null, null);
                if(result.isEmpty()){
                    s = "";
                    for(int j = 0; j < i; j++){
                        s += " " + inArr[j];
                    }
                    s = s.replaceFirst(" ", "");
                } else {
                    bestRated = result.getResults().get(0);
                    for(MovieInfo m : result.getResults()) {
                        if (removeArticles(normalize(m.getTitle())).equals(removeArticles(s))) {
                            ic.addData(m.getTitle());
                            ic.addData(String.valueOf(m.getId()));
                            ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_ASK_MORE);
                            return ic;
                        } else {
                            if(m.getVoteAverage() > bestRated.getVoteAverage()){
                                bestRated = m;
                            }
                        }
                    }
                }
            }
            if(ic.getData().isEmpty()){
                s = in;
                for (int i = 0; i < inArr.length; i++){
                    // TODO de als language
                    ResultList<MovieInfo> result = Bot.tmdbSearch.searchMovie(s, 0, null, false, null, null, null);
                    if(result.isEmpty()){
                        s = "";
                        for(int j = inArr.length-1; j > i; j--){
                            s = inArr[j] + " " + s;
                        }
                        if(!s.equals("")) s = s.substring(0,s.length()-1);
                    } else {
                        for(MovieInfo m : result.getResults()) {
                            if (removeArticles(normalize(m.getTitle())).equals(removeArticles(s))) {
                                ic.addData(m.getTitle());
                                ic.addData(String.valueOf(m.getId()));
                                ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_ASK_MORE);
                                return ic;
                            } else {
                                if(m.getVoteAverage() > bestRated.getVoteAverage()){
                                    bestRated = m;
                                }
                            }
                        }
                        ic.addData(bestRated.getTitle());
                        ic.addData(String.valueOf(bestRated.getId()));
                        ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_ASK_CONFIRM);
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
