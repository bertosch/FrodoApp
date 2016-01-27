package de.spdmc.frodo.profile;

//import java.beans.XMLDecoder;


import de.spdmc.frodo.profile.Profile;

public class ProfileReader {
	
	public Profile read() throws Exception {

        /*XMLDecoder decoder =
            new XMLDecoder(new BufferedInputStream(
                new FileInputStream("/vol/fob-vol6/mi13/radtkede/git/Frodo/profile.xml")));
        Profile o = (Profile)decoder.readObject();
        decoder.close();*/
        return new Profile();
    }
	
}
