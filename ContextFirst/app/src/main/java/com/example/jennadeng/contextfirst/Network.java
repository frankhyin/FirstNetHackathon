package com.example.jennadeng.contextfirst;


import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Network {

    String baseUrl = "https://6c3476f0.ngrok.io/";

    private static Network singletonInstance;

    private OkHttpClient client;

    private Network() {
        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
    }

    public static Network getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new Network();
        }
        return singletonInstance;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void closeConnections() {
        client.dispatcher().cancelAll();
    }

    public void postPoint(RequestBody formBody, String endPoint){
        Request request = new Request.Builder()
                .url(baseUrl + endPoint)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("result", e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // success case
                String result = response.body().string();
                Log.e("result", result);
            }
        });
    }


    String getPoint(String endPoint) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + endPoint)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
