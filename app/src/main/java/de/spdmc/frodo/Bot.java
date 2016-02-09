package de.spdmc.frodo;

import android.content.Context;
import android.util.Log;

import com.omertron.themoviedbapi.methods.TmdbSearch;
import com.omertron.themoviedbapi.tools.HttpTools;

import org.apache.http.client.HttpClient;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

import java.util.Random;

import de.spdmc.frodo.connection.Connection;
import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.profile.Profile;
import de.spdmc.frodo.profile.ProfileReader;
import de.spdmc.frodo.profile.ProfileWriter;
import de.spdmc.frodo.textparser.ActorParser;
import de.spdmc.frodo.textparser.FavoriteTypeParser;
import de.spdmc.frodo.textparser.GenreParser;
import de.spdmc.frodo.textparser.GreetingsParser;
import de.spdmc.frodo.textparser.InputContent;
import de.spdmc.frodo.textparser.MoviesParser;
import de.spdmc.frodo.textparser.NameParser;
import de.spdmc.frodo.textparser.NameWithdrawParser;
import de.spdmc.frodo.textparser.QueryParser;
import de.spdmc.frodo.textparser.RecommendationParser;
import de.spdmc.frodo.textparser.TvShowParser;
import de.spdmc.frodo.textparser.YesNoParser;

public class Bot {

    //API Tools
    public static String apiKey = "ccea4a6c65c6edba1535e8e8014b0e77";
    public static HttpClient httpClient = new SimpleHttpClientBuilder().build();
    private static HttpTools httpTools = new HttpTools(httpClient);
    public static TmdbSearch tmdbSearch = new TmdbSearch(apiKey, httpTools);

    private static String TAG = "Bot";
    private static Context context;
    private static Enumerations.DialogState currentState = Enumerations.DialogState.PARSE_NAME;
    private static Profile p = new Profile();
    private static ProfileReader reader = new ProfileReader();
    private static ProfileWriter writer = new ProfileWriter();
    private static InputContent savedIc;

    public static void readSavedProfile(){
        try {
            p = reader.read();
        } catch (Exception e) {
            Log.e(TAG, "Noch kein gespeichertes Profil vorhanden");
        }
    }

    private static void writeProfile() {
        try {
            writer.write(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateReply(String s) {
        String reply = null;
        // initialisiere Parser
        GreetingsParser greetingsParser = new GreetingsParser();
        QueryParser queryParser = new QueryParser();
        NameParser nameParser = new NameParser();
        NameWithdrawParser nameWithdrawParser = new NameWithdrawParser(p);
        FavoriteTypeParser favTypeParser = new FavoriteTypeParser();
        GenreParser genreParser = new GenreParser();
        ActorParser actorParser = new ActorParser();
        RecommendationParser recommendationParser = new RecommendationParser();
        MoviesParser moviesParser = new MoviesParser();
        TvShowParser tvShowParser = new TvShowParser();
        YesNoParser ynParser = new YesNoParser(savedIc, p);

        InputContent ic = new InputContent(); // InputContent der mit jeweiligem geparsten Inhalt gefuellt wird

        while (reply == null) {
            // Fallunterscheidung fuer Auswertung:
            // Je nach aktuellem Status wird jeweiliger Bot angewandt um User Input auszuwerten.
            switch (currentState) {
                case PARSE_GREETING:
                    ic = greetingsParser.parse(s);
                    // Wenn greetingsParser nicht trifft werden andere Bot probiert.
                    if (ic.getDialogState() == null) ic = nameParser.parse(s, true);
                    //if (ic.getDialogState() == null) ic = queryParser.parse(s);
                    if (ic.getDialogState() == null) ic = nameWithdrawParser.parse(s);
                    if (ic.getDialogState() == null) ic = recommendationParser.parse(s);
                    if (ic.getDialogState() == null)
                        ic.setDialogState(Enumerations.DialogState.GREETING_REPLY);
                    currentState = ic.getDialogState();
                    break;
                case PARSE_QUERY:
                    ic = queryParser.parse(s);
                    // Wenn queryParser nicht trifft werden andere Bot probiert.
                    if (ic.getDialogState() == null) ic = nameWithdrawParser.parse(s);
                    if (ic.getDialogState() == null) ic = nameParser.parse(s);
                    if (ic.getDialogState() == null) ic = greetingsParser.parse(s);
                    if (ic.getDialogState() == null) ic = recommendationParser.parse(s);
                    currentState = ic.getDialogState();
                    break;
                case PARSE_NAME:
                    ic = nameParser.parse(s, true);
                    //if (ic.getDialogState() == null) ic = queryParser.parse(s);
                    if (ic.getDialogState() == null) ic = nameWithdrawParser.parse(s);
                    if (ic.getDialogState() == null) ic = recommendationParser.parse(s);
                    currentState = ic.getDialogState();
                    if(currentState == null) currentState = Enumerations.DialogState.NAME_REASK;
                    break;
                case PARSE_FAVORITE_TYPE:
                    ic = favTypeParser.parse(s);
                    //if (ic.getDialogState() == null) ic = queryParser.parse(s);
                    if (ic.getDialogState() == null) ic = nameWithdrawParser.parse(s);
                    if (ic.getDialogState() == null) ic = recommendationParser.parse(s);
                    currentState = ic.getDialogState();
                    if(currentState == null) currentState = Enumerations.DialogState.FAVORITE_TYPE_FAULT_REPLY;
                    break;
                case PARSE_GENRE:
                    ic = genreParser.parse(s, true);
                    if (ic.getDialogState() == null) ic = recommendationParser.parse(s);
                    // TODO andere Parser laufen lassen
                    currentState = ic.getDialogState();
                    if(currentState == null) currentState = Enumerations.DialogState.GENRE_FAULT_REPLY;
                    break;
                case PARSE_FAVORITE_ACTOR:
                    ic = actorParser.parse(s);
                    if (ic.getDialogState() == null) ic = recommendationParser.parse(s);
                    currentState = ic.getDialogState();
                    // TODO andere Parser laufen lassen
                    if(currentState == null) currentState = Enumerations.DialogState.FAVORITE_ACTOR_FAULT_REPLY;
                    break;
                case PARSE_FAVORITE_MOVIE:
                    ic = moviesParser.parse(s);
                    if (ic.getDialogState() == null) ic = recommendationParser.parse(s);
                    currentState = ic.getDialogState();
                    // TODO andere Parser laufen lassen
                    if(currentState == null) currentState = Enumerations.DialogState.FAVORITE_MOVIES_REASK;
                    break;
                case PARSE_FAVORITE_MOVIE_YES_NO:
                    ic = ynParser.parse(s);
                    if (ic.getDialogState() == null) ic = recommendationParser.parse(s);
                    currentState = ic.getDialogState();
                    break;
                case PARSE_FAVORITE_TVSHOW:
                    ic = tvShowParser.parse(s);
                    if (ic.getDialogState() == null) ic = recommendationParser.parse(s);
                    currentState = ic.getDialogState();
                    // TODO andere Parser laufen lassen
                    if(currentState == null) currentState = Enumerations.DialogState.FAVORITE_TVSHOW_REASK;
                    break;
                case PARSE_FAVORITE_TVSHOW_YES_NO:
                    ic = ynParser.parse(s);
                    if (ic.getDialogState() == null) ic = recommendationParser.parse(s);
                    currentState = ic.getDialogState();
                    break;
                case PARSE_RECOMMENDATION_YES_NO:
                    ic = ynParser.parse(s);
                    currentState = ic.getDialogState();
                    break;
                case GREETING_REPLY:
                    reply = speakRandomlyAppend(greetingsParser.getPattern(), ", wie ist dein Name?");
                    currentState = Enumerations.DialogState.PARSE_GREETING;
                    break;
                case QUERY_REPLY:
                    for (String q : ic.getData()) {
                        reply = "starting query for " + q;
                    }
                    // TODO neuen currentState setzeminemen
                    break;
                case NAME_REPLY:
                    reply = "Freut mich dich kennenzulernen, "
                            + ic.getData().get(0) + speakRandomly(new String[]{
                            "! Bist du auf der Suche nach Filmen oder Serien?",
                            "! Wonach suchst du, Filme oder Serien?",
                            "! Wonach suchen wir für dich? Filme oder Serien?",
                            "! Was wäre dir jetzt lieber? Ein Film oder eine neue Serie?"
                    });
                    p.setName(ic.getData().get(0));
                    writeProfile();
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_TYPE;
                    break;
                case NAME_REASK:
                    reply = speakRandomly(new String[]{
                            "Willst du mir deinen Namen verraten?",
                            "Wie heißt du eigentlich?",
                            "Wir können gleich loslegen, wenn du mir deinen Namen verraten hast!"
                    });
                    currentState = Enumerations.DialogState.PARSE_NAME;
                    break;
                case NAME_REASK2:
                    reply = "Wie ist denn dein Name?";
                    currentState = Enumerations.DialogState.PARSE_NAME;
                    break;
                case NAME_DECLINED:
                    reply = speakRandomly(new String[]{
                            "Okay, schade! ",
                            "Soso, dann ohne Namen ;)! ?",
                            "Schade, ich hätte gern deinen Namen erfahren. "

                    }) + speakRandomly(new String[]{
                            "Bist du auf der Suche nach Filmen oder Serien ? ",
                            "Was suchen wir für dich, einen Film oder eine Serie",
                            "Wie gehts weiter, Film oder Serie?"
                    });
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_TYPE;
                    break;
                case FAVORITE_TYPE_REASK:
                    reply = "Filme oder Serien?";
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_TYPE;
                    break;
                case NAME_WITHDRAW:
                    reply = "Entschuldigung, dann habe ich das falsch "
                            + "verstanden! Wie ist denn dein Name?";
                    p.setName(null);
                    writeProfile();
                    currentState = Enumerations.DialogState.PARSE_NAME;
                    break;
                case FAVORITE_TYPE_REPLY:
                    String type = ic.getData().get(0);
                    if(type.equals("film")) type = "Filme";
                    else if(type.equals("serie")) type = "Serien";
                    reply = speakRandomly(new String[]{
                            "Okay, du magst also ",
                            "Super, ich mag auch ",
                            "Alles klar, wer mag keine "
                    }) + type +
                            speakRandomly(new String[]{
                                    "... Gibt es ein Genre, indem ich für dich suchen soll?",
                                    "... Dann fahren wir fort. Gibt es ein Genre?",
                                    "... Genre?"
                            });
                    p.setFavorite_type(ic.getData().get(0));
                    writeProfile();
                    currentState = Enumerations.DialogState.PARSE_GENRE;
                    break;
                case GENRE_REPLY:
                    String name = ic.getData().get(0);
                    name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
                    reply = speakRandomly(new String[]{
                            "Dein Lieblingsgenre ist also " + name,
                            "Du stehst also auf " + name,
                            "Du magst also " + name
                    }) + speakRandomly(new String[]{
                            ". Hast du eine/n LieblingsschauspielerIn?",
                            ". Gibt es eine/n SchauspielerIn, den du besonders toll findest?",
                            ". Darf es auch ein/e bestimmte/r SchauspielerIn sein?"
                    });
                    p.setFavorite_genre(ic.getData().get(0));
                    p.setFavorite_genre_id(ic.getData().get(1));
                    writeProfile();
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_ACTOR;
                    break;
                case GENRE_FAULT_REPLY:
                    reply = speakRandomly(new String[]{
                            "Das habe ich leider nicht verstanden. ",
                            "Oops, damit kann ich nichts anfangen! "
                    }) + speakRandomly(new String[]{
                            "Hast du ein Genre, das du besonders magst?",
                            "Gib mir bitte ein Genre, auf das du gerade Bock hast!"
                    });
                    currentState = Enumerations.DialogState.PARSE_GENRE;
                    break;
                case FAVORITE_TYPE_FAULT_REPLY:
                    reply = speakRandomly(new String[]{
                            "Das habe ich leider nicht verstanden. ",
                            "Oops, damit kann ich nichts anfangen! "
                    }) + speakRandomly(new String[]{
                            "Bist du auf der Suche nach Filmen oder Serien?",
                            "Film oder Serie?",
                            "Ich kann dir ohne diese Angabe nicht weiterhelfen. Also suchen wir einen Film oder eine Serie für dich?"
                    });
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_TYPE;
                    break;
                case FAVORITE_ACTOR_REASK:
                    reply = speakRandomly(new String[]{
                            "Magst du mir den Namen verraten?",
                            "Wie heißt denn eben jener?",
                            "Hat er auch einen Namen?",
                            "Ich kann leider nicht deine Gedanken lesen... Den Namen müsstest du mir schon verraten!",

                    });
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_ACTOR;
                    break;
                case FAVORITE_ACTOR_DECLINED:
                    if(p.getFavorite_type().equals("film")){
                        reply = speakRandomly(new String[]{
                                "Okay, Hast du einen Lieblingsfilm?",
                                "Nun gut, gibt es denn einen Film, den du besonders toll fandest?",
                                "Ich auch nicht! Aber vielleicht einen Lieblingsfilm? Meiner ist Herr der Ring!!!"
                        });
                        currentState = Enumerations.DialogState.PARSE_FAVORITE_MOVIE;
                    }
                    else {
                        reply = speakRandomly(new String[]{
                                "Okay, Hast du eine Lieblingsserie?",
                                "Gut, gibts eine Serie, die du toll fandest?",
                                "Wer kennt schon Seriendarsteller namentlich? Gibt es denn eine Lieblingsserie?"

                        });
                        currentState = Enumerations.DialogState.PARSE_FAVORITE_TVSHOW;
                    }
                    break;
                case FAVORITE_ACTOR_REPLY:
                    reply = "Okay, du magst also " + ic.getData().get(0) + ".";
                    p.setFavorite_actor(ic.getData().get(0));
                    p.setFavorite_actor_id(ic.getData().get(1));
                    Log.d(TAG, p.getFavorite_actor() + ", " + p.getFavorite_actor_id());
                    writeProfile();
                    if(p.getFavorite_type().equals("film")){
                        reply += speakRandomly(new String[]{
                                ". Hast du auch einen Lieblingsfilm?",
                                ", gibt es denn einen Film, den du besonders toll fandest?"
                        });
                        currentState = Enumerations.DialogState.PARSE_FAVORITE_MOVIE;
                    }
                    else {
                        reply += " Hast du eine Lieblingsserie?";
                        currentState = Enumerations.DialogState.PARSE_FAVORITE_TVSHOW;
                    }
                    break;
                case FAVORITE_ACTOR_FAULT_REPLY:
                    reply = speakRandomly(new String[]{
                            "Den kenne ich leider nicht. ",
                            "Damit kann ich leider nichts anfangen. "
                    }) +
                            "Wer war nochmal dein/e LieblingsschauspielerIn";


                    currentState = Enumerations.DialogState.PARSE_FAVORITE_ACTOR;
                    break;
                case GENRE_DECLINED:
                    reply = "Okay, du hast also kein spezielles Lieblingsgenre. Hast du einen Lieblingsschauspieler?";
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_ACTOR;
                    break;
                case GENRE_REASK:
                    reply = "Wie lautet dein Lieblingsgenre?";
                    currentState = Enumerations.DialogState.PARSE_GENRE;
                    break;
                case FAVORITE_MOVIES_REASK:
                    reply = "Wie lautet dein Lieblingsfilm?";
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_MOVIE;
                    break;
                case FAVORITE_MOVIES_DECLINED:
                    reply = "Okay. Soll ich dir anhand der Informationen die ich über dich habe Vorschläge machen?";
                    savedIc = ic;
                    currentState = Enumerations.DialogState.PARSE_RECOMMENDATION_YES_NO;
                    break;
                case FAVORITE_MOVIES_ASK_MORE:
                    reply = "Hast du noch weitere Lieblingsfilme?";
                    p.addFavorite_movie(ic.getData().get(0));
                    p.addWatched_movie(ic.getData().get(0));
                    writeProfile();
                    Log.d(TAG, ic.getData().get(0) + ", " + ic.getData().get(1));
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_MOVIE;
                    break;
                case FAVORITE_MOVIES_ASK_CONFIRM:
                    reply = "Meinst du " + ic.getData().get(0) + "?";
                    savedIc = ic;
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_MOVIE_YES_NO;
                    break;
                case FAVORITE_MOVIES_IN_ADDITION:
                    reply = "Hast du zusaetzlich auch einen Lieblingsfilm?";
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_MOVIE;
                    break;
                case FAVORITE_TVSHOW_REASK:
                    reply = "Wie lautet deine Lieblingsserie?";
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_TVSHOW;
                    break;
                case FAVORITE_TVSHOW_DECLINED:
                    reply = "Okay. Soll ich dir anhand der Informationen die ich über dich habe Vorschläge machen?";
                    savedIc = ic;
                    currentState = Enumerations.DialogState.PARSE_RECOMMENDATION_YES_NO;
                    break;
                case FAVORITE_TVSHOW_ASK_MORE:
                    reply = "Hast du noch weitere Lieblingsserien?";
                    p.addFavorite_serie(ic.getData().get(0));
                    p.addWatched_serie(ic.getData().get(0));
                    writeProfile();
                    Log.d(TAG, ic.getData().get(0) + ", " + ic.getData().get(1));
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_TVSHOW;
                    break;
                case FAVORITE_TVSHOW_ASK_CONFIRM:
                    reply = "Meinst du " + ic.getData().get(0) + "?";
                    savedIc = ic;
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_TVSHOW_YES_NO;
                    break;
                case FAVORITE_TVSHOW_IN_ADDITION:
                    reply = "Hast du zusaetzlich auch eine Lieblingsserie?";
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_TVSHOW;
                    break;
                case RECOMMEND:
                    reply = "Ich empfehle dir ";
                    if(p.getFavorite_type() == null){
                        reply = speakRandomly(new String[]{
                                "Du musst mir erst einmal sagen, ob du nach einer Serie oder nach einem Film suchst.",
                                "Wonach suchst du, Filme oder Serien?"
                        });
                        currentState = Enumerations.DialogState.PARSE_FAVORITE_TYPE;
                    }else {
                        try {
                            Connection con = new Connection(p);
                            con.discover();
                            reply += con.getTitle();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        reply += "\nIch hoffe ich konnte dir weiterhelfen! Bis zum nächsten mal.";
                        currentState = Enumerations.DialogState.GOODBYE;
                    }
                    break;
                //TODO
                // Alternative Verabschiedung, wenn nicht nach Serien oder Filmen gesucht werden soll
                /*case ALTERNATE_GOODBYE:
                    break;
                 */
                case GOODBYE:
                    reply = "Ich hoffe ich konnte dir weiterhelfen! Bis zum nächsten mal.";
                    break;
                default:
                    reply = "Das habe ich leider nicht richtig verstanden.";
                    break;
            }
        }
        return reply;
    }

    private static String speakRandomly(String[] arr) {
        Random r = new Random();
        int randInt = r.nextInt(arr.length);
        return arr[randInt];
    }

    private static String speakRandomlyAppend(String[] arr, String s){
        Random r = new Random();
        int randInt = r.nextInt(arr.length);
        String out = arr[randInt];
        return out + s;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Bot.context = context;
    }

    public static void setCurrentState(Enumerations.DialogState dialogState){
        currentState = dialogState;
    }

    public static String normalize(String s) {
        String normalizedStr = s.replaceAll("[^a-zA-Z 0-9äöüß]", "");
        normalizedStr = normalizedStr.toLowerCase();
        return normalizedStr;
    }

    public static void setProfile(Profile p){
        Bot.p = p;
    }
}
