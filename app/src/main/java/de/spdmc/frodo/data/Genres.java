package de.spdmc.frodo.data;

import de.spdmc.frodo.data.GermanGenre;

public class Genres {

    public static GermanGenre[] genres = new GermanGenre[19];

    public static void fillGenres(){
        genres[0] = new GermanGenre("action","action",28);
        genres[1] = new GermanGenre("adventure","abenteuer",12);
        genres[2] = new GermanGenre("animation","zeichentrick",16);
        genres[3] = new GermanGenre("comedy","komödie",35);
        genres[4] = new GermanGenre("crime","krimi",80);
        genres[5] = new GermanGenre("documentary","dokumentation",99);
        genres[6] = new GermanGenre("drama","drama",18);
        genres[7] = new GermanGenre("family","familie",10751);
        genres[8] = new GermanGenre("fantasy","fantasie",14);
        genres[9] = new GermanGenre("history","geschicht",36); // geschicht als substring fuer geschichtlich, geschichte etc.
        genres[10] = new GermanGenre("horror","horror",27);
        genres[11] = new GermanGenre("music","musik",10402);
        genres[12] = new GermanGenre("mystery","myster",9648); // myster als substring fuer mysterioes (?) :D
        genres[13] = new GermanGenre("romance","roman",10749); // roman als substring fuer romanze, romantisch etc.
        genres[14] = new GermanGenre("science fiction","science fiction",878);
        genres[15] = new GermanGenre("tv movie","fernsehfilm",10770);
        genres[16] = new GermanGenre("thriller","thriller",53);
        genres[17] = new GermanGenre("war","krieg",10752);
        genres[18] = new GermanGenre("western","western",37);
    }

}
