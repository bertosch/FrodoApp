package de.spdmc.frodo.data;

import com.omertron.themoviedbapi.model.Genre;

public class GermanGenre extends Genre {

    private String germanName;

    public GermanGenre(String name, String germanName, int id){
        this.setName(name);
        this.setGermanName(germanName);
        this.setId(id);
    }

    public String getGermanName() {
        return germanName;
    }

    public void setGermanName(String germanName) {
        this.germanName = germanName;
    }
}
