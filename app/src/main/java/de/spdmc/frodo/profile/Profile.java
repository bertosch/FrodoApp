package de.spdmc.frodo.profile;

import java.util.ArrayList;
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
    private  List<String> favorite_movies = new ArrayList<>();
    private  List<String> favorite_series = new ArrayList<>();
    private String favorite_genre_id = null;
    private String favorite_actor_id = null;

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

    public void read()
    {
        /*xpp = factory.newPullParser();
        file = new File(getFilesDir()+"/Profile.xml");
        fis = new FileInputStream(file);
        xpp.setInput(new InputStreamReader(fis));
        eventType = 0;
        eventType = xpp.getEventType();
        do
        {
            if (eventType == XmlResourceParser.START_TAG)
            {
                String strName = xpp.getName();
                if (strName.equals("Shop"))
                {
                    String nameSh = xpp.getAttributeValue(null, "name");

                }
            }
            eventType = xpp.next();
        }
        while (eventType != XmlResourceParser.END_DOCUMENT);*/
    }

    public String getFavorite_genre_id() {
        return favorite_genre_id;
    }

    public void setFavorite_genre_id(String favorite_genre_id) {
        this.favorite_genre_id = favorite_genre_id;
    }

    public String getFavorite_actor_id() {
        return favorite_actor_id;
    }

    public void setFavorite_actor_id(String favorite_actor_id) {
        this.favorite_actor_id = favorite_actor_id;
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
            this.favortie_actor = pr.read().getFavorite_actor();
            this.favortie_genre = pr.read().getFavorite_genre();
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
