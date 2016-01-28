package de.spdmc.frodo.profile;

//import java.beans.XMLDecoder;
import android.os.Environment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.xml.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import de.spdmc.frodo.Bot;

public class ProfileReader {
	
	public Profile read() throws Exception {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        Profile profile = new Profile();

        String path = Bot.getContext().getFilesDir().toString();
        FileInputStream fis = new FileInputStream(path + "/" + "profile.xml");
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

                //TODO Listen

            } else if(eventType == XmlPullParser.END_TAG) {
            } else if(eventType == XmlPullParser.TEXT) {
                // Do nothing
            }
            eventType = xpp.next();
        }

        return profile;
    }
	
}
