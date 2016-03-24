package de.spdmc.frodo.connection;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.enumeration.SearchType;
import com.omertron.themoviedbapi.enumeration.SortBy;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.model.tv.TVInfo;
import com.omertron.themoviedbapi.results.ResultList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.spdmc.frodo.Bot;
import de.spdmc.frodo.profile.Profile;

public class Connection {
    private TheMovieDbApi tmdb;
    private int page = 1;
    private List<MovieBasic> resultMovie;
    private List<TVBasic> resultTV;
    private List<MovieInfo> similarMovies = new ArrayList<>();
    private List<TVInfo> similarTVs = new ArrayList<>();
    private String favorite_type;
    private Profile p;
    private TVInfo tvinfo;
    private MovieInfo movieinfo;

    public Connection(Profile p) throws Exception {
        tmdb = new TheMovieDbApi(Bot.apiKey, Bot.httpClient);
        this.p = p;
    }

    public void discover() throws Exception {
        Discover discover = new Discover();
        discover.language("de").sortBy(SortBy.POPULARITY_DESC).page(page);

        discover.withGenres(p.getFavorite_genre_id());
        String actorID = peopleToID(p.getFavorite_actor()); //kann auch duch p.getFavorite_actor_id ersetzt werden wenn profil vollstaendig gespeichert wird

        if (!actorID.equals("")) {
            discover.withCast(actorID);
        }

        //Unterscheidung Serien oder Filme
        this.favorite_type = p.getFavorite_type();
        if (favorite_type.equals("serie")) {
            discoverTV(discover);
        } else {
            discoverMovie(discover);
        }
    }

    //Falls Titel nicht gewollt/bereits geguckt etc, naechsten Titel ausgeben, evtl naechste Seite aus der API holen
    public void setNext() throws Exception {
        if (favorite_type.equals("serie")){
            resultTV.remove(0);
            if (resultTV.isEmpty()){
                page++;
                discover();
            }
        }else{
            resultMovie.remove(0);
            if (resultMovie.isEmpty()){
                page++;
                discover();
            }
        }
    }

    public String getInfo() throws MovieDbException {
        String info = null;
        if (favorite_type.equals("serie")){
            if (!resultTV.isEmpty()){
                System.out.println(resultTV.size());
                tvinfo = tmdb.getTVInfo(resultTV.get(0).getId(),"de");
                info = getTitle() + " (Release: " + getFirstAirDate() + "; " + "Genres:" + getGenresTV() + "; " +"Episodendauer:" + getRuntimeTV() + "; " + "Episodenanzahl:" + getNumberOfEpisodes() + ")";
            }
        }else {
            if (!resultMovie.isEmpty()) {
                movieinfo = tmdb.getMovieInfo(resultMovie.get(0).getId(), "de");
                info = getTitle() + " (Release: " + getReleaseDate() + "; " + "Genres: " + getGenresMovie() + "; " + "Dauer: " + getRuntimeMovie() + " min)";
            }
        }
        return info;
    }

    //Kurzbeschreibung
    public String getOverview() throws MovieDbException {
        if (favorite_type.equals("serie")){
            return tvinfo.getOverview();
        }else{
            return movieinfo.getOverview();
        }
    }

    // cast Liste ist immer leer
    /*public ArrayList<String> getActors(){
        if (favorite_type.equals("serie")){
            return null; //TODO Schauspieler fuer Serien
        }else{
            ArrayList<String> actors = new ArrayList<>();
            try {
                MovieInfo m = tmdb.getMovieInfo(resultMovie.get(0).getId(), null);
                List<MediaCreditCast> cast = m.getCast();
                List<MediaCreditCrew> crew = m.getCrew();
            } catch (MovieDbException e) {
                e.printStackTrace();
            }
            for(MediaCreditCast mcc : movieinfo.getCast()) {
                actors.add(mcc.getName());
            }
            return actors;
        }
    }*/

    public String getTitle(){
        if (favorite_type.equals("serie")){
            return resultTV.get(0).getName();
        }else{
            return resultMovie.get(0).getTitle();
        }
    }

    private void discoverTV(Discover discover) throws Exception {
        resultTV = tmdb.getDiscoverTV(discover).getResults();
        similarTVs();
        for (int i = 0; i < resultTV.size(); i++) {
            //Alle schon geguckten Serien loeschen
            if (p.getWatched_series().contains(resultTV.get(i).getName())) {
                resultTV.remove(i);
            } else {
                //Schnittmenge von similarTVs und resultTV nach vorne bringen
                for (TVInfo similarTV : similarTVs) {
                    if (similarTV.getName().equals(resultTV.get(i).getName())) {
                        TVBasic move = resultTV.get(i);
                        resultTV.remove(i);
                        resultTV.add(0, move);
                    }
                }
            }
        }
    }
    private void discoverMovie(Discover discover) throws Exception {
        resultMovie = tmdb.getDiscoverMovies(discover).getResults();
        similarMovies();
        for (int i=0; i<resultMovie.size();i++) {
            //Alle schon geguckten Filme loeschen
            if (p.getWatched_movies().contains(resultMovie.get(i).getTitle())){
                resultMovie.remove(i);
                i--;
            }else{
                //Schnittmenge von similarMovies und resultMovie nach vorne bringen
                for (MovieInfo similarMovy : similarMovies) {
                    if (similarMovy.getTitle().equals(resultMovie.get(i).getTitle())) {
                        MovieBasic move = resultMovie.get(i);
                        resultMovie.remove(i);
                        resultMovie.add(0, move);
                    }
                }
            }
        }
    }
    //Schauspieler-ID holen
    private String peopleToID(String actorID)  {
        String ID="";
        try {
            ResultList<PersonFind> persons = tmdb.searchPeople(actorID,null,null, SearchType.PHRASE);
            ID = String.valueOf(persons.getResults().get(0).getId());
        } catch (MovieDbException e) {
            e.printStackTrace();
        }
        return ID;
    }
    //aehnliche Filme von den Lieblingsfilmen holen
    private void similarMovies() throws MovieDbException {
        if(!p.getFavorite_movies().isEmpty()) {
            int id;
            String title = p.getFavorite_movies().get(0);
            id = tmdb.searchMovie(title, 0, "de", false, null, null, SearchType.PHRASE).getResults().get(0).getId();
            similarMovies = tmdb.getSimilarMovies(id, 0, "de").getResults();
            for (int i = 1; i < p.getFavorite_movies().size(); i++) {
                title = p.getFavorite_movies().get(i);
                id = tmdb.searchMovie(title, 0, "de", false, null, null, SearchType.PHRASE).getResults().get(0).getId();
                similarMovies.addAll(tmdb.getSimilarMovies(id, 0, "de").getResults());
            }
        }
    }
    //aehnliche Serien von den Lieblingsserien holen
    private void similarTVs() throws MovieDbException {
        if(!p.getFavorite_series().isEmpty()) {
            int id;
            String title = p.getFavorite_series().get(0);
            id = tmdb.searchTV(title, 0, "de", null, SearchType.PHRASE).getResults().get(0).getId();
            similarTVs = tmdb.getTVSimilar(id, 0, "de").getResults();
            for (int i = 1; i < p.getFavorite_series().size(); i++) {
                title = p.getFavorite_series().get(i);
                id = tmdb.searchTV(title, 0, "de", null, SearchType.PHRASE).getResults().get(0).getId();
                similarTVs.addAll(tmdb.getTVSimilar(id, 0, "de").getResults());
            }
        }
    }
    private String getGenresTV() throws MovieDbException {
        List<Genre> c;
        c = tvinfo.getGenres();
        String genres = "[";
        if (!c.isEmpty()){
            genres += c.get(0).getName();
            for (int i=1; i<c.size();i++){
                genres += ", " + c.get(i).getName();
            }
        }

        genres += "]";
        return genres;
    }
    private String getGenresMovie() throws MovieDbException {
        List<Genre> c;
        c = movieinfo.getGenres();
        String genres = "[";
        if (!c.isEmpty()){
            genres += c.get(0).getName();
            for (int i=1; i<c.size();i++){
            genres += ", " + c.get(i).getName();
            }
        }
        genres += "]";
        return genres;
    }
    private String getRuntimeTV() throws MovieDbException {
        List<Integer> l = tvinfo.getEpisodeRunTime();
        String s;
        if (l.size()>1){
            s = Collections.min(l) + "-" + Collections.max(l) + "min";
        }else{
            if(!l.isEmpty()) {
                s = l.get(0).toString() + "min";
            }else{
                s = "n.a."; //nicht angegeben
            }
        }
        return s;
    }
    private String getRuntimeMovie() throws MovieDbException {
        return Integer.toString(movieinfo.getRuntime());
    }
    private String getFirstAirDate()throws MovieDbException{
        return tvinfo.getFirstAirDate();
    }
    private String getReleaseDate() throws MovieDbException {
            return resultMovie.get(0).getReleaseDate();
    }
    private int getNumberOfEpisodes() throws MovieDbException {
        return tvinfo.getNumberOfEpisodes();
    }
}