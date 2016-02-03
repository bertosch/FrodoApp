package de.spdmc.frodo.connection;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.enumeration.SearchType;
import com.omertron.themoviedbapi.enumeration.SortBy;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.person.PersonFind;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.results.ResultList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.spdmc.frodo.Bot;
import de.spdmc.frodo.profile.Profile;


public class Connection {
    private TheMovieDbApi tmdb;
    private int current = 0;
    private int page = 0;
    private String name;
    private ResultList<MovieBasic> resultMovie;
    private ResultList<TVBasic> resultTV;
    private List<Integer> array = new ArrayList<>();
    private String favorite_type;
    private Profile p;


    public Connection(Profile p) throws MovieDbException {
        tmdb = new TheMovieDbApi(Bot.apiKey, Bot.httpClient);
        this.p = p;
    }

    public void discover() throws Exception {
        Discover discover = new Discover();
        discover.language("de").sortBy(SortBy.POPULARITY_DESC).page(page);

        discover.withGenres(p.getFavorite_genre_id());
        name = peopleToID(p.getFavorite_actor()); //kann auch duch p.getFavorite_actor_id ersetzt werden wenn profil vollstaendig gespeichert wird

        this.favorite_type = p.getFavorite_type();

        if (!name.equals("")){discover.withCast(name);}


        //Unterscheidung Serien oder Filme
        int size;
        if (favorite_type.equals("serie")){
            resultTV = tmdb.getDiscoverTV(discover);
            size = resultTV.getResults().size();
        }else{
            resultMovie = tmdb.getDiscoverMovies(discover);
            size = resultMovie.getResults().size();
        }


        //ArrayList mit Zahlen von 0-19 füllen und shufflen
        if (size < 20){
            for (int i = 0; i<size; i++) {
                array.add(i);
            }
        }else{
            for (int i = 0; i<20; i++) {
                array.add(i);
            }
        }
        Collections.shuffle(array);

        if (favorite_type.equals("serie")){
            for (int i=0; i<size;i++) {
                if (p.getWatched_series().contains(getTitle())){
                    setNext();
                }else{
                    break;
                }
            }
        }else{
            for (int i=0; i<size;i++) {
                if (p.getWatched_movies().contains(getTitle())){
                    setNext();
                }else{
                    break;
                }
            }
        }
    }

    public String getTitle(){
        if (favorite_type.equals("serie")){
            return resultTV.getResults().get(array.get(current)).getName();
        }else{
            return resultMovie.getResults().get(array.get(current)).getTitle();
        }

    }

    //Falls Titel nicht gewollt/bereits geguckt etc, nächsten Titel ausgeben, evtl nächste Seite aus der API holen
    public void setNext() throws Exception {
        if (current < array.size()){
            current++;
        }else {
            array.clear();
            page++;
            current =0;
            discover();
        }
    }
    //Schauspieler-ID holen
    private String peopleToID(String name)  {
        String ID="";
        try {
            ResultList<PersonFind> persons = tmdb.searchPeople(name,null,null, SearchType.PHRASE);
            ID = String.valueOf(persons.getResults().get(0).getId());
        } catch (MovieDbException e) {}
        return ID;
    }

    public String getReleaseDate(){
        if (favorite_type.equals("serie")){
            return resultTV.getResults().get(array.get(current)).getFirstAirDate();
        }else{
            return resultMovie.getResults().get(array.get(current)).getReleaseDate();
        }
    }
    //geht nur für Filme, nicht fuer Serien
    public String getOverview(){
        if (!favorite_type.equals("serie")){
            return resultMovie.getResults().get(array.get(current)).getOverview();
        }else{
            return null;
        }
    }

}