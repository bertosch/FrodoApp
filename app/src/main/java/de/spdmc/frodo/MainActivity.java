package de.spdmc.frodo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

import de.spdmc.frodo.data.Genres;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    //Methoden für die Views/Button
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = false;
    private ResponseReceiver receiver;
    private ChatMessage thinkingMessage = new ChatMessage(true, "Frodo denkt nach...");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bot.setContext(this.getApplicationContext());
        Bot.readSavedProfile();
        Genres.fillGenres();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        buttonSend = (Button) findViewById(R.id.send);
        listView = (ListView) findViewById(R.id.msgoverview);
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.single_message);
        listView.setAdapter(chatArrayAdapter);
        chatText = (EditText) findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });

        //Send Button für Versand scharf machen
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // nur senden wenn Texteingabe nicht leer
                if(!chatText.getText().toString().equals("")) sendChatMessage();
                else Toast.makeText(getApplicationContext(), "Nachricht darf nicht leer sein!", Toast.LENGTH_SHORT).show();
            }
        });

        //zum Ende scrollen, wenn neue Nachrichten kommen
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        chatArrayAdapter.add(new ChatMessage(true,
                "Hallo, mein Name ist Frodo, dein Film- und Serienberater. Wie ist dein Name?"));
    }

    //KLasse ChatMessage aufrufen und Nachricht versenden
    private boolean sendChatMessage() {
        String in = chatText.getText().toString();
        chatArrayAdapter.add(new ChatMessage(false, in));
        chatText.setText("");

        chatArrayAdapter.add(thinkingMessage);
        //Intent Service
        Intent frodoIntent = new Intent(MainActivity.this, BotService.class);
        frodoIntent.setAction(BotService.ACTION_QUESTION);
        frodoIntent.putExtra(BotService.EXTRA_QUESTION, in);
        startService(frodoIntent);
        //ALT
        //chatArrayAdapter.add(new ChatMessage(true, Bot.generateReply(in)));
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            try {
                String path = Bot.getContext().getFilesDir().toString();
                File file = new File(path + "/" + "profile.xml");
                boolean deleted = file.delete();
                Log.v("log_tag", "deleted: " + deleted);
                Toast.makeText(getApplicationContext(), "Das Profil wurde gelöscht", Toast.LENGTH_SHORT).show();
                this.onCreate(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // registriere den ResponseReceiver, so dass er auf BROADCAST_ACTION_BOT_ANSWER anspringt
        IntentFilter intentFilter = new IntentFilter(
                IntentActions.BROADCAST_ACTION_BOT_ANSWER);
                intentFilter.addAction(IntentActions.BROADCAST_ACTION_BOT_STATUS);
        receiver = new ResponseReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver, intentFilter);

    }


    // Empfaenger fuer Broadcast aus dem Service
    private class ResponseReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //nur zum gucken ob was passiert oder nicht. kann wieder raus später
            //wird nur funktionieren wenn in der onResume BROADCAST_ACTION_BOT_STATUS auch dem intentFilter hinzugefuegt wird (Lars)
            if (intent.getAction().equalsIgnoreCase(IntentActions.BROADCAST_ACTION_BOT_STATUS)) {
                int extraMsg = intent.getIntExtra(IntentActions.EXTRA_BOT_STATUS, 0);
                switch (extraMsg){
                    case IntentActions.STATUS_BOT_LOADING:
                        Toast.makeText(MainActivity.this, "Frodo wird geladen", Toast.LENGTH_SHORT).show();
                        break;

                    case IntentActions.STATUS_BOT_FULLY_LOADED:
                        Toast.makeText(MainActivity.this, "Frodo geladen", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            if (intent.getAction().equalsIgnoreCase(IntentActions.BROADCAST_ACTION_BOT_ANSWER)){
                chatArrayAdapter.remove(thinkingMessage);
                chatArrayAdapter.add(new ChatMessage(true, intent.getExtras().getString(IntentActions.EXTRA_BOT_ANSWER)));
            }
        }
    }

}
