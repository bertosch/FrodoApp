package de.spdmc.frodo;

/**
 * Created by rob on 28.01.16.
 */
public final class IntentActions {

        // Intent Actions
        public static final String BROADCAST_ACTION_BOT_STATUS =
                "de.spdmc.frodo.BROADCAST_ACTION_BOT_STATUS";
        public static final String BROADCAST_ACTION_BOT_ANSWER =
                "de.spdmc.frodo.BROADCAST_ACTION_BOT_ANSWER";

        // for extra Questions to put in intent while executing
        public static final String EXTRA_BOT_STATUS =
                "de.spdmc.frodo.EXTRA_BOT_STATUS";
        public static final String EXTRA_BOT_ANSWER =
                "de.spdmc.frodo.EXTRA_BOT_ANSWER";

        //status Bot fully loaded/loading
        public static final int STATUS_BOT_LOADING = -1;
        public static final int STATUS_BOT_FULLY_LOADED = 1;

}
