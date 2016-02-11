package de.spdmc.frodo;

/**
 * @Author
 * Created by rob on 24.01.16.
 */

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    private TextView chatText;
    private List<ChatMessage> chatMessageList = new ArrayList<>();
    private Context context;
    private LinearLayout singleMessage;

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    @Override
    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_message, parent, false);
        }
        singleMessage = (LinearLayout) row.findViewById(R.id.singleMessageLin);
        ChatMessage chatMessageObj = getItem(position);
        chatText = (TextView) row.findViewById(R.id.singleMessage);
        chatText.setText(chatMessageObj.message);
        chatText.setBackgroundResource(chatMessageObj.left ? R.drawable.txtview2 : R.drawable.txtview);
        singleMessage.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);
        return row;
    }

    @Override
    public void remove(ChatMessage object) {
        chatMessageList.remove(object);
    }
}
