package de.spdmc.frodo.profile;

import java.util.List;

public class Profile {


    public Profile()
    {
        /**************************************/
    }

    //Properties

    private  String name = null;
    private  String favorite_type = null;
    private  String favorite_actor = null;
    private  String favorite_genre = null;
    private  int favorite_genre_id;
    private  List<String> favorite_movies = null;
    private  List<String> favorite_series = null;

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

    public void addWachted_movie(String mv) {
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

    public int getFavorite_genre_id() {
        return favorite_genre_id;
    }

    public void setFavorite_genre_id(int favorite_genre_id) {
        this.favorite_genre_id = favorite_genre_id;
    }



    /*// Profil schreiben --> Pfad im Writer angeben
    public void write()
    {
        ProfileWriter pw = new ProfileWriter();
        try {
            pw.write(this);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    // Profil lesen --> Pfad im Reader angeben
    public void read()
    {
        ProfileReader pr = new ProfileReader();

        try {
            //TODO Properties setzen
            this.name =  pr.read().getName();
            this.favorite_type = pr.read().getFavorite_type();
            this.favorite_actor = pr.read().getFavorite_actor();
            this.favorite_genre = pr.read().getFavorite_genre();
            this.favorite_movies = pr.read().getFavorite_movies();
            this.favorite_series = pr.read().getFavorite_series();
            this.watched_movies = pr.read().getWatched_movies();
            this.watched_series = pr.read().getWatched_series();
            this.last_watched = pr.read().getLast_watched();

        } catch (Exception e) {
            // #########################
        }
    }*/
}
