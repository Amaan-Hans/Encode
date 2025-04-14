package com.example.encode;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class internetrequests {
    private static final String TAG = "internetrequests";

    public static String return_ID(String username){
        OkHttpClient client;
        String[] valid = {"false"}; //need to use this variable outside of "run" but java forces you to make it final so i made it an array
        client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2541305/get_id.php?username=" + username;
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    valid[0] = responseBody;


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } ;
        Thread th = new Thread(r);
        th.start();
        try {
            // Wait for the thread to finish
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return valid[0];
    }
    public static String get_questions(){
        Log.i(TAG, "get_questions function called sucessfully");
        OkHttpClient client;
        String[] valid = {""}; //need to use this variable outside of "run" but java forces you to make it final so i made it an array
        client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2541305/questions.php";
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    final String responseBody = response.body().string();
                    JSONArray all = new JSONArray(responseBody);


                    JSONObject item = all.getJSONObject(0);
                    String lic = item.getString("question_id");
                    Log.i(TAG, lic);

                    valid[0] = responseBody;


                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        } ;
        Thread th = new Thread(r);
        th.start();
        try {
            // Wait for the thread to finish
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return valid[0];
    }
    public static String get_comments(String Q_ID) {
        Log.i(TAG, "get_comments function called sucessfully");
        OkHttpClient client;
        String[] valid = {""}; //need to use this variable outside of "run" but java forces you to make it final so i made it an array
        client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2541305/comments.php?questionID=" + Q_ID;
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    final String responseBody = response.body().string();
                    valid[0] = responseBody;


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread th = new Thread(r);
        th.start();
        try {
            // Wait for the thread to finish
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return valid[0];
    }
    public static String return_username(String id){
        OkHttpClient client;
        String[] valid = {"false"}; //need to use this variable outside of "run" but java forces you to make it final so i made it an array
        client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2541305/get_username.php?id=" + id;
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    valid[0] = responseBody;


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } ;
        Thread th = new Thread(r);
        th.start();
        try {
            // Wait for the thread to finish
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return valid[0];
    }
    public static String like_question_request(String question_id, String user_id){
        OkHttpClient client;
        String[] valid = {"something wrong happened"}; //need to use this variable outside of "run" but java forces you to make it final so i made it an array
        client = new OkHttpClient();
        Log.i(TAG, "question id ="+ question_id + "  user_id= " + user_id);
        String url = "https://lamp.ms.wits.ac.za/home/s2541305/like.php?question_id="+ question_id + "&user_id=" + user_id;
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    valid[0] = responseBody;
                    Log.i(TAG, "updated valid 0 successfully"+ valid[0]);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } ;
        Thread th = new Thread(r);
        th.start();
        try {
            // Wait for the thread to finish
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "returns this:" + valid[0]);
        return valid[0];

    }
    public static String like_comment_request(String comment_id, String user_id){
        OkHttpClient client;
        String[] valid = {"something wrong happened"}; //need to use this variable outside of "run" but java forces you to make it final so i made it an array
        client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2541305/commentlike.php?comment_id="+ comment_id + "&user_id=" + user_id;
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    valid[0] = responseBody;
                    Log.i(TAG, "updated valid 0 successfully"+ valid[0]);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } ;
        Thread th = new Thread(r);
        th.start();
        try {
            // Wait for the thread to finish
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "returns this:" + valid[0]);
        return valid[0];

    }

    public static String liked_question(String user_id, String question_id){
        OkHttpClient client;
        String[] valid = {"something wrong happened"}; //need to use this variable outside of "run" but java forces you to make it final so i made it an array
        client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2541305/liked.php?question_id="+ question_id + "&user_id=" + user_id;
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    valid[0] = responseBody;
                    Log.i(TAG, "updated valid 0 successfully"+ valid[0]);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } ;
        Thread th = new Thread(r);
        th.start();
        try {
            // Wait for the thread to finish
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "returns this:" + valid[0]);
        return valid[0];
    }
}
