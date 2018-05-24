package com.knacky.events.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.knacky.events.R;
import com.knacky.events.data.entities.messagge.ChatMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by knacky on 11.03.2018.
 */

public class ChatFragment extends Fragment {
    @BindView(R.id.fab)
    FloatingActionButton sendMessageBtn;

    @BindView(R.id.input)
    TextInputEditText inputMessageEditTxt;

    @BindView(R.id.list_of_messages)
    ListView listOfMessages;

    private FirebaseListAdapter<ChatMessage> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View chatFragmentView = inflater.inflate(R.layout.chat_room, null, false);
        ButterKnife.bind(this, chatFragmentView);
        initButtons();
        displayChat();
        return chatFragmentView;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }

    private void initButtons() {
        sendMessageBtn.setOnClickListener(v -> {        //Send message button
            // Read the input field and push a new instance
            // of ChatMessage to the Firebase database
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Messages")
                    .push()
                    .setValue(new ChatMessage(inputMessageEditTxt.getText().toString(),
                            FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getDisplayName())
                    );

            // Clear the input
            inputMessageEditTxt.setText("");
            displayChat();
        });
    }

    public void displayChat() {
        //Suppose you want to retrieve "chats" in your Firebase DB:
        Query query = FirebaseDatabase.getInstance().getReference().child("Messages");

        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage.class)

                .setLifecycleOwner(this)            // important  syka line!!!!!
                .build();
        Log.v("Chat", "Display Chat< fireBaseOptions done");

        adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                Log.v("Chat", "Model: " + model.toString() + "\nuser: " + model.getMessageUser());
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}
