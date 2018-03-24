package com.example.jennadeng.contextfirst;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BasicActivity extends MainActivity {

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buttonSend = (Button) findViewById(R.id.send);

        listView = (ListView) findViewById(R.id.msgview);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right);
        listView.setAdapter(chatArrayAdapter);

        final Network localSingleton = Network.getInstance();
        OkHttpClient client = localSingleton.getClient();
        client.newCall(new Request.Builder()
                .url("https://6c3476f0.ngrok.io/" + "texts")
                .build()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // ... check for failure using `isSuccessful` before proceeding

                // Read data on the worker thread
                final String responseData = response.body().string();

                // Run view-related code back on the main thread
                BasicActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Message messages [] = gson.fromJson(responseData, Message[].class);
                        boolean alt = true;
                        for(int i =0; i < messages.length; i++){
                            alt = !alt;
                            chatArrayAdapter.add(new ChatMessage(alt, messages[i].getMessage()));
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("result", e.toString());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatMessage currentMessage = chatArrayAdapter.getItem(position);
                if(currentMessage != null) {
                    if(currentMessage.left){
                        final Network localSingleton = Network.getInstance();
                        OkHttpClient client = localSingleton.getClient();
                        client.newCall(new Request.Builder()
                                .url("https://6c3476f0.ngrok.io/" + "newestText")
                                .build()).enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                // ... check for failure using `isSuccessful` before proceeding

                                // Read data on the worker thread
                                final String responseData = response.body().string();

                                // Run view-related code back on the main thread
                                BasicActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Gson gson = new Gson();
                                        Message messages [] = gson.fromJson(responseData, Message[].class);
                                        chatArrayAdapter.add(new ChatMessage(false, messages[0].getMessage()));
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e("result", e.toString());
                            }
                        });
                    }
                }else {

                }
            }
        });

        chatText = (EditText) findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    /*public void onUpdate(View v) {

        Network localSingleton = Network.getInstance();
        try {
            String newMessage = localSingleton.getPoint("newestText");
            chatArrayAdapter.add(new ChatMessage(true, newMessage));
        } catch (IOException e){
            Log.e("get error", e.toString());
        }
    }*/

    private boolean sendChatMessage() {
        chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString()));
        chatText.setText("");
        side = !side;
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_basic;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_basic;
    }

}
