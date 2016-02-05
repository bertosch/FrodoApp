package de.spdmc.frodo;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * Created by rob on 04.02.16.
 */
public class ChatLogger {
    private static final String TAG = "ChatLogger";
    private static int time = (int) (System.currentTimeMillis());
    private static Timestamp tsTemp = new Timestamp(time);
    private static String ts =  tsTemp.toString();
    private static String path = Bot.getContext().getExternalFilesDir(null).toString();
    private static File file =new File(path + "/" + "chatlog" + ts + ".txt");
    private static String state = Environment.getExternalStorageState();


/*
    public static File getExtStorageDir(Context context, String Logs) {
        // Get the directory for the app's private pictures directory.
        File Dir = new File(context.getExternalFilesDir(
                null), "Logs");
        if (!Dir.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return Dir;
    }
*/
    public static void writeToFile (String data){

        try{
            //TODO
            //SAVE to external
            if(!file.exists() && Environment.MEDIA_MOUNTED.equals(state)){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(data);
            Log.d(TAG, "writeToFile():" + data);
            pw.println("");

            pw.close();

        }catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }
    }
}

