package de.spdmc.frodo;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


/**
 * Created by rob on 28.01.16.
 */
public class BotService extends IntentService {
    private static final String TAG = "BotService";
    public static final String ACTION_QUESTION = "de.spdmc.frodo.BotService.ACTION_QUESTION";
    public static final String ACTION_STOP = "de.spdmc.frodo.BotService.ACTION_STOP";
    public static final String ACTION_START = "de.spdmc.frodo.BotService.ACTION_START";
    public static final String EXTRA_QUESTION = "de.spdmc.frodo.BotService.EXTRA_QUESTION";

    public BotService(){
        super("BotService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BotService(String name) {
        super(name);
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (intent == null) {
            return Service.START_STICKY;
        }

        String action = intent.getAction();

        if(action.equalsIgnoreCase(ACTION_STOP)){
            stopForeground(true);
            stopSelf();
            return Service.START_NOT_STICKY;
        }

        if(action.equalsIgnoreCase(ACTION_START)){ //  && bot==null habe ich nicht verstanden (Lars)
            (new StartBotThread()).start();
        }

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        if (action.equalsIgnoreCase(ACTION_QUESTION)) {

            String question = intent.getStringExtra(EXTRA_QUESTION);
            if (question != null) {
                String answer;
                /*if (bot != null) {
                    answer = bot.generateReply(question);
                } else {
                    answer = "Gimme some info, dude!";
                }*/
                answer = Bot.generateReply(question);

                Intent localIntent =
                        new Intent(IntentActions.BROADCAST_ACTION_BOT_ANSWER)
                                // Answer into Intent
                                .putExtra(IntentActions.EXTRA_BOT_ANSWER, answer);
                //
                // Broadcast of Intent to Receiver in-app
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                localBroadcastManager.sendBroadcast(localIntent);
            }

        }
    }

    //TODO
    //Thread for starting
    private final class StartBotThread extends Thread {
        @Override
        public void run() {

        }
    }
}
