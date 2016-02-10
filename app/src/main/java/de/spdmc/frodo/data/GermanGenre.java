package de.spdmc.frodo.data;

import com.omertron.themoviedbapi.model.Genre;

public class GermanGenre {

    private String name;
    private String[] pattern;
    private String id;

    public GermanGenre(String name, String[] pattern, int id){
        this.setName(name);
        this.setPattern(pattern);
        this.setId(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = String.valueOf(id);
    }

    public String getId() {
        return id;
    }

    public String[] getPattern() {
        return pattern;
    }

    public void setPattern(String[] pattern) {
        this.pattern = pattern;
    }
}
