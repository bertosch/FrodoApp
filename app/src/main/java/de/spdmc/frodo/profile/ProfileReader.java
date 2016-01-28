package de.spdmc.frodo.profile;

import android.os.Environment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;

import de.spdmc.frodo.Bot;

public class ProfileReader {
	
	public Profile read() throws Exception {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        Profile profile = new Profile();


        String test = Environment.getExternalStorageDirectory().getAbsolutePath();
        String path = Bot.getContext().getFilesDir().toString();
        FileInputStream fis = new FileInputStream(test + "/" + "profile.xml");
        xpp.setInput(fis, null);
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
                //################
            } else if(eventType == XmlPullParser.END_DOCUMENT) {
                //################
            } else if(eventType == XmlPullParser.START_TAG) {
                if(xpp.getName().equals("name"))
                {
                    String s = xpp.nextText();
                    if(!s.equals("")) profile.setName(s);
                }
                else if(xpp.getName().equals("favorite_type"))
                {
                    String s = xpp.nextText();
                    if(!s.equals("")) profile.setFavorite_type(s);
                }
                else if(xpp.getName().equals("favorite_actor"))
                {
                    String s = xpp.nextText();
                    if(!s.equals("")) profile.setFavorite_actor(s);
                }
                else if(xpp.getName().equals("favorite_genre"))
                {
                    String s = xpp.nextText();
                    if(!s.equals("")) profile.setFavorite_genre(s);
                }
                else if(xpp.getName().equals("last_watched"))
                {
                    String s = xpp.nextText();
                    if(!s.equals("")) profile.setLast_watched(s);
                }
                else if(xpp.getName().equals("favorite_genre_id"))
                {
                    String s = xpp.nextText();
                    if(!s.equals("")) profile.setFavorite_genre_id(s);
                }
                else if(xpp.getName().equals("movie"))
                {
                    String s = xpp.nextText();
                    if(!s.equals("")) profile.addFavorite_movie(s);
                }
                else if(xpp.getName().equals("watched movie"))
                {
                    String s = xpp.nextText();
                    if(!s.equals("")) profile.addWatched_movie(s);
                }
                else if(xpp.getName().equals("serie"))
                {
                    String s = xpp.nextText();
                    if(!s.equals("")) profile.addFavorite_serie(s);
                }
                else if(xpp.getName().equals("watched serie"))
                {
                    String s = xpp.nextText();
                    if(!s.equals("")) profile.addWatched_serie(s);
                }

            } else if(eventType == XmlPullParser.END_TAG) {
            } else if(eventType == XmlPullParser.TEXT) {
                // Do nothing
            }
            eventType = xpp.next();
        }

        return profile;
    }
	
}
