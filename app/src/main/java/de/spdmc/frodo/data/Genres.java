package de.spdmc.frodo.data;

import de.spdmc.frodo.data.GermanGenre;

public class Genres {

    public static GermanGenre[] genres = new GermanGenre[19];

    public static void fillGenres(){
        genres[0] = new GermanGenre("Action", new String[] {"action"}, 28);
        genres[1] = new GermanGenre("Abenteuer", new String[]{"adventure","abenteuer"}, 12);
        genres[2] = new GermanGenre("Zeichentrick", new String[]{"animation","zeichentrick"}, 16);
        genres[3] = new GermanGenre("Comedy", new String[]{"comedy","komödie","sitcom"}, 35);
        genres[4] = new GermanGenre("Krimi", new String[]{"crime","krimi"}, 80);
        genres[5] = new GermanGenre("Dokumentation", new String[]{"documentary","dokumentation"}, 99);
        genres[6] = new GermanGenre("Drama", new String[]{"drama","arztserie"}, 18);
        genres[7] = new GermanGenre("Familie", new String[]{"family","familie"}, 10751);
        genres[8] = new GermanGenre("Fantasy", new String[]{"fantasy","fantasie"}, 14);
        genres[9] = new GermanGenre("Geschichte", new String[]{"history","geschicht"}, 36); // geschicht als substring fuer geschichtlich, geschichte etc.
        genres[10] = new GermanGenre("Horror", new String[]{"horror"}, 27);
        genres[11] = new GermanGenre("Musik", new String[]{"music","musik"}, 10402);
        genres[12] = new GermanGenre("Mystery", new String[]{"mystery","myster"}, 9648); // myster als substring fuer mysterioes (?) :D
        genres[13] = new GermanGenre("Romanze", new String[]{"romance","roman"}, 10749); // roman als substring fuer romanze, romantisch etc.
        genres[14] = new GermanGenre("Science Fiction", new String[]{"science fiction","science fiction"}, 878);
        genres[15] = new GermanGenre("Fernsehfilm", new String[]{"tv movie","fernsehfilm"}, 10770);
        genres[16] = new GermanGenre("Thriller", new String[]{"thriller"}, 53);
        genres[17] = new GermanGenre("Krieg", new String[]{"krieg"}, 10752);
        genres[18] = new GermanGenre("Western", new String[]{"western"}, 37);
    }

}
