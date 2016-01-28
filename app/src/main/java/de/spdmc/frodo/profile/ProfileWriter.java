package de.spdmc.frodo.profile;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.*;

import de.spdmc.frodo.Bot;

public class ProfileWriter {
	 
	public void write(Profile profile) throws Exception{

		//String path = Bot.getContext().getFilesDir().toString();
		Log.d("DEBUG", Environment.getExternalStorageDirectory().getAbsolutePath());
		String path = Environment.getExternalStorageDirectory().getAbsolutePath();
		FileOutputStream fos1 = new FileOutputStream(path + "/" + "profile.xml");
		XmlSerializer xs = Xml.newSerializer();
		StringWriter sw = new StringWriter();
		try
		{
			xs.setOutput(sw);
			xs.startDocument("UTF-8", true);

			//Name
			xs.startTag(null, "name");
			if(profile.getName() != null) xs.text(profile.getName());
			else xs.text("");
			xs.endTag(null, "name");

			//Type
			xs.startTag(null, "favorite_type");
			if(profile.getFavorite_type() != null) xs.text(profile.getFavorite_type());
			else xs.text("");
			xs.endTag(null, "favorite_type");

			//Actor
			xs.startTag(null, "favorite_actor");
			if(profile.getFavorite_actor() != null) xs.text(profile.getFavorite_actor());
			else xs.text("");
			xs.endTag(null, "favorite_actor");

			//Genre
			xs.startTag(null, "favorite_genre");
			if(profile.getFavorite_genre() != null) xs.text(profile.getFavorite_genre());
			else xs.text("");
			xs.endTag(null, "favorite_genre");

			//Last watched
			xs.startTag(null, "last_watched");
			if(profile.getLast_watched() != null) xs.text(profile.getLast_watched());
			else xs.text("");
			xs.endTag(null, "last_watched");

			//TODO Listen

			xs.endDocument();

			fos1.flush();
			fos1.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
}
