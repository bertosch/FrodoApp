package de.spdmc.frodo.profile;

import android.os.Environment;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.*;

import de.spdmc.frodo.Bot;

public class ProfileWriter {
	 
	public void write(Profile profile) throws Exception{

        String test = Environment.getExternalStorageDirectory().getAbsolutePath();;
		String path = Bot.getContext().getFilesDir().toString();
		FileOutputStream fos1 = new FileOutputStream(test + "/" + "profile.xml");
		XmlSerializer xs = Xml.newSerializer();
		try
		{
			xs.setOutput(fos1, "UTF-8");
			xs.startDocument("UTF-8", true);

			//Name
			xs.startTag(null, "name");
			if(profile.getName() != null) xs.text(profile.getName());
			xs.endTag(null, "name");

			//Type
			xs.startTag(null, "favorite_type");
			if(profile.getFavorite_type() != null) xs.text(profile.getFavorite_type());
			xs.endTag(null, "favorite_type");

			//Actor
			xs.startTag(null, "favorite_actor");
			if(profile.getFavorite_actor() != null) xs.text(profile.getFavorite_actor());
			xs.endTag(null, "favorite_actor");

			//Genre
			xs.startTag(null, "favorite_genre");
			if(profile.getFavorite_genre() != null) xs.text(profile.getFavorite_genre());
			xs.endTag(null, "favorite_genre");

			//Last watched
			xs.startTag(null, "last_watched");
			if(profile.getLast_watched() != null) xs.text(profile.getLast_watched());
			xs.endTag(null, "last_watched");


			//genre ID
            xs.startTag(null, "favorite_genre_id");
            if(profile.getFavorite_genre_id() != null) xs.text(profile.getFavorite_genre_id());
            xs.endTag(null, "favorite_genre_id");

            //Favorite Movies
            xs.startTag(null, "favorite_movies");
            if(profile.getFavorite_movies() != null) {
                for(String s : profile.getFavorite_movies()) {
                    xs.startTag(null, "movie");
                    xs.text(s);
                    xs.endTag(null, "movie");
                }
            }
            xs.endTag(null, "favorite_movies");

            //Favorite Series
            xs.startTag(null, "favorite_series");
            if(profile.getFavorite_series() != null) {
                    for(String s : profile.getFavorite_series()) {
                        xs.startTag(null, "serie");
                        xs.text(s);
                        xs.endTag(null, "serie");
                    }
            }
            xs.endTag(null, "favorite_series");


            //Watched Movies
            xs.startTag(null, "watched_movies");
            if(profile.getWatched_movies() != null) {
                for(String s : profile.getWatched_movies()) {
                        xs.startTag(null, "watched movie");
                        xs.text(s);
                        xs.endTag(null, "watched movie");
                }
            }
            xs.endTag(null, "watched_movies");


            //Watched Series
            xs.startTag(null, "watched_series");
            if(profile.getWatched_series() != null) {
                for(String s : profile.getWatched_series()) {
                    xs.startTag(null, "watched serie");
                    xs.text(s);
                    xs.endTag(null, "watched serie");
                }
            }
            xs.endTag(null, "watched_series");

            xs.endDocument();

			fos1.flush();
			fos1.close();

		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
}
