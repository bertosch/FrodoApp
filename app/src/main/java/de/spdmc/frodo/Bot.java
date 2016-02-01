package de.spdmc.frodo;

import android.content.Context;
import android.util.Log;

import java.util.Random;

import de.spdmc.frodo.enumerations.Enumerations;
import de.spdmc.frodo.profile.Profile;
import de.spdmc.frodo.profile.ProfileReader;
import de.spdmc.frodo.profile.ProfileWriter;
import de.spdmc.frodo.textparser.ActorParser;
import de.spdmc.frodo.textparser.FavoriteTypeParser;
import de.spdmc.frodo.textparser.GenreParser;
import de.spdmc.frodo.textparser.GreetingsParser;
import de.spdmc.frodo.textparser.InputContent;
import de.spdmc.frodo.textparser.NameParser;
import de.spdmc.frodo.textparser.NameWithdrawParser;
import de.spdmc.frodo.textparser.QueryParser;
import de.spdmc.frodo.textparser.RecommendationParser;

public class Bot {

    private static String TAG = "Bot";
    private static Context context;
    private static Enumerations.DialogState currentState = Enumerations.DialogState.PARSE_NAME;
    private static Profile p = new Profile();
    private static ProfileReader reader = new ProfileReader();
    private static ProfileWriter writer = new ProfileWriter();

    public static void readSavedProfile(){
        try {
            p = reader.read();
        } catch (Exception e) {
            Log.e(TAG,"Noch kein gespeichertes Profil vorhanden");
            e.printStackTrace();
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

        InputContent ic = new InputContent(); // InputContent der mit jeweiligem geparsten Inhalt gefuellt wird

        while (reply == null) {
            // Fallunterscheidung fuer Auswertung:
            // Je nach aktuellem Status wird jeweiliger Bot angewandt um User Input auszuwerten.
            switch (currentState) {
                case PARSE_GREETING:
                    ic = greetingsParser.parse(s);
                    // Wenn greetingsParser nicht trifft werden andere Bot probiert.
                    if (ic.getDialogState() == null) ic = nameParser.parse(s, true);
                    if (ic.getDialogState() == null) ic = queryParser.parse(s);
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
                    if (ic.getDialogState() == null) ic = queryParser.parse(s);
                    if (ic.getDialogState() == null) ic = nameWithdrawParser.parse(s);
                    if (ic.getDialogState() == null) ic = recommendationParser.parse(s);
                    currentState = ic.getDialogState();
                    if(currentState == null) currentState = Enumerations.DialogState.NAME_REASK;
                    break;
                case PARSE_FAVORITE_TYPE:
                    ic = favTypeParser.parse(s);
                    if (ic.getDialogState() == null) ic = queryParser.parse(s);
                    if (ic.getDialogState() == null) ic = nameWithdrawParser.parse(s);
                    if (ic.getDialogState() == null) ic = nameParser.parse(s);
                    if (ic.getDialogState() == null) ic = greetingsParser.parse(s);
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
                    if(currentState == null) currentState = Enumerations.DialogState.FAVORITE_ACTOR_REASK;
                    break;
                case GREETING_REPLY:
                    reply = speakRandomlyAppend(greetingsParser.getPattern(), ", wie ist dein Name?");
                    currentState = Enumerations.DialogState.PARSE_GREETING;
                    break;
                case QUERY_REPLY:
                    for (String q : ic.getData()) {
                        reply = "starting query for " + q;
                    }
                    // TODO neuen currentState setzen
                    break;
                case NAME_REPLY:
                    reply = "Freut mich dich kennenzulernen, "
                            + ic.getData().get(0) + "! Magst du lieber Filme oder Serien?";
                    p.setName(ic.getData().get(0));
                    writeProfile();
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_TYPE;
                    break;
                case NAME_REASK:
                    reply = "Willst du mir deinen Namen verraten?";
                    currentState = Enumerations.DialogState.PARSE_NAME;
                    break;
                case NAME_REASK2:
                    reply = "Wie ist denn dein Name?";
                    currentState = Enumerations.DialogState.PARSE_NAME;
                    break;
                case NAME_DECLINED:
                    reply = "Okay, schade! Magst du lieber Filme oder Serien?";
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
                    reply = "Okay, du magst also " + type + ". hast du ein bestimmtes Lieblingsgenre?";
                    p.setFavorite_type(ic.getData().get(0));
                    writeProfile();
                    currentState = Enumerations.DialogState.PARSE_GENRE;
                    break;
                case GENRE_REPLY:
                    reply = "Dein Lieblingsgenre ist also " + ic.getData().get(0) + ". Hast du einen Lieblingsschauspieler?";
                    p.setFavorite_genre(ic.getData().get(0));
                    p.setFavorite_genre_id(ic.getData().get(1));
                    writeProfile();
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_ACTOR;
                    break;
                case GENRE_FAULT_REPLY:
                    reply = "Das habe ich leider nicht verstanden. Welches Genre magst du?";
                    currentState = Enumerations.DialogState.PARSE_GENRE;
                    break;
                case FAVORITE_TYPE_FAULT_REPLY:
                    reply = "Das habe ich leider nicht verstanden. Magst du lieber Filme oder Serien?";
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_TYPE;
                    break;
                case FAVORITE_ACTOR_REASK:
                    reply = "Schön, magst du mir den Namen verraten?";
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_ACTOR;
                    break;
                case FAVORITE_ACTOR_DECLINED:
                    reply = "Okay, [ASK NEXT QUESTION]";
                    //TODO next State
                    break;
                case FAVORITE_ACTOR_REPLY:
                    reply = "Okay, du magst also " + ic.getData().get(0) + ". [ASK NEXT QUESTION]";
                    // TODO next State
                    break;
                case RECOMMEND:
                    reply = "Suche passende Filme / Serien"; // Dummy
                    // TODO TMDB-API Anfrage anhand vorhandener Profildaten
                    // TODO currentState =
                    break;
                case GENRE_DECLINED:
                    reply = "Okay, du hast also kein spezielles Lieblingsgenre. Hast du einen Lieblingsschauspieler?";
                    currentState = Enumerations.DialogState.PARSE_FAVORITE_ACTOR;
                    break;
                case GENRE_REASK:
                    reply = "Schön, wie lautet dein Lieblingsgenre?";
                    currentState = Enumerations.DialogState.PARSE_GENRE;
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
}
