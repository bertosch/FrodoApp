package de.spdmc.frodo;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by rob on 01.02.16.
 */
public class LoadBotService extends IntentService {
    public LoadBotService() {
        super("LoadBotService");
    }
    @Override
    protected void onHandleIntent(Intent workIntent) {
        //TODO
        //Wuerde den Bot gern hier initial aufrufen! Lars kannst du das möglich machen?
        //Bot.setup(this);

        Intent localIntent =
                new Intent(IntentActions.BROADCAST_ACTION_BOT_STATUS)
                        // Status into the Intent
                        .putExtra(IntentActions.EXTRA_BOT_STATUS, IntentActions.STATUS_BOT_FULLY_LOADED);
        // Intent-Broadcast to receivers in this app
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
