package de.spdmc.frodo.profile;

import java.util.ArrayList;
import java.util.List;

public class Profile {


    public Profile()
    {
        favorite_movies = new ArrayList<String>();
        favorite_series = new ArrayList<String>();
        watched_movies = new ArrayList<String>();
        watched_series = new ArrayList<String>();
    }

    //Properties

    private  String name = null;
    private  String favorite_type = null;
    private  String favorite_actor = null;
    private  String favorite_genre = null;
    private  List<String> favorite_movies = null;
    private  List<String> favorite_series = null;
    private String favorite_genre_id = null;

    private  String last_watched = null;

    private  List<String> watched_movies = null;
    private  List<String> watched_series = null;

    //getter, setter

    public  String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavorite_type() {
        return favorite_type;
    }

    public void setFavorite_type(String favorite_type) {
        this.favorite_type = favorite_type;
    }

    public String getFavorite_actor() {
        return favorite_actor;
    }

    public void setFavorite_actor(String actor) {
        this.favorite_actor = actor;
    }

    public String getFavorite_genre() {
        return favorite_genre;
    }

    public void setFavorite_genre(String genre) {
        this.favorite_genre = genre;
    }

    public List<String> getFavorite_movies() {
        return favorite_movies;
    }

    public void addFavorite_movie(String mv) {
        favorite_movies.add(mv);
    }

    public List<String> getFavorite_series() {
        return favorite_series;
    }

    public void addFavorite_serie(String serie) {
        favorite_series.add(serie);
    }

    public List<String> getWatched_movies() {
        return watched_movies;
    }

    public void addWatched_movie(String mv) {
        watched_movies.add(mv);
    }

    public List<String> getWatched_series() {
        return watched_series;
    }

    public void addWatched_serie(String serie) {
        watched_series.add(serie);
    }

    public String getLast_watched()
    {
        return this.last_watched;
    }

    public void setLast_watched( String lw){
        this.last_watched = lw;
    }

       public String getFavorite_genre_id() {
        return favorite_genre_id;
    }

    public void setFavorite_genre_id(String favorite_genre_id) {
        this.favorite_genre_id = favorite_genre_id;
    }
}
