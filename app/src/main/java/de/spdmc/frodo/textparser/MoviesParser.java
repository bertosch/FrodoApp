package de.spdmc.frodo.textparser;

import android.util.Log;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.results.ResultList;

import de.spdmc.frodo.Bot;
import de.spdmc.frodo.enumerations.Enumerations;

/**
 * Created by lars on 02.02.16.
 */
public class MoviesParser extends Parser {
    @Override
    public InputContent parse(String in) {
        in = normalize(in);
        InputContent ic = new InputContent();
        String[] inArr = in.split(" ");

        if (inArr.length == 1){ // nur ja oder nein als Antwort
            if(inArr[0].equals("ja")){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_REASK);
                return ic;
            }
            else if(inArr[0].equals("nein")){
                ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_DECLINED);
                return ic;
            }
        }
        try {
            String s = in;
            for (int i = inArr.length-1; (i >= inArr.length-3) && (i >= 0); i--){
                ResultList<MovieInfo> result = Bot.tmdbSearch.searchMovie(s, 0, null, false, null, null, null);
                if(result.isEmpty()){
                    s = "";
                    for(int j = 0; j <= i-1; j++){
                        s += inArr[j];
                    }
                } else if (!removeArticles(normalize(result.getResults().get(0).getTitle()))
                        .equals(removeArticles(s))){ // erstes suchresultat matched suchname nicht
                    Log.d("MoviesParser",result.getResults().get(0).getTitle() + " does not equal " + s);
                    s = "";
                    for(int j = 0; j <= i-1; j++){
                        s += inArr[j];
                    }
                } else {
                    ic.addData(result.getResults().get(0).getTitle());
                    ic.addData(String.valueOf(result.getResults().get(0).getId()));
                    ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_ASK_MORE);
                    return ic;
                }
            }
            if(ic.getData().isEmpty()){
                s = inArr[0];
                for (int i = 0; (i <= 1) && (i < inArr.length-1); i++){
                    ResultList<MovieInfo> result = Bot.tmdbSearch.searchMovie(s, 0, null, false, null, null, null);
                    if(result.isEmpty()){
                        s += " " + inArr[i+1];
                    } else if (!normalize(result.getResults().get(0).getTitle().replace("the", "")
                            .replace("der", "").replace("die", "").replace("das", ""))
                            .equals(s)){ // erstes suchresultat matched suchname nicht
                        Log.d("MoviesParser",result.getResults().get(0).getTitle() + " does not equal " + s);
                        s += " " + inArr[i+1];
                    } else {
                        ic.addData(result.getResults().get(0).getTitle());
                        ic.addData(String.valueOf(result.getResults().get(0).getId()));
                        ic.setDialogState(Enumerations.DialogState.FAVORITE_MOVIES_ASK_MORE);
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
