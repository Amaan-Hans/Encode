package com.example.encode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.example.encode.internetrequests.get_comments;
import static com.example.encode.internetrequests.like_comment_request;
import static com.example.encode.internetrequests.like_question_request;
import static com.example.encode.internetrequests.liked_question;
import static com.example.encode.sortdata.ValidString;
import static com.example.encode.internetrequests.return_ID;
import static com.example.encode.internetrequests.return_username;
import static com.example.encode.internetrequests.get_questions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String valid_username = "";
    String valid_ID = "";
    private static final String TAG = "MainActivity";
    private OkHttpClient client;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_page);

    }
    public void login_page(View v){
        setContentView(R.layout.login_details);
    }
    public void signup_page(View v){
        setContentView(R.layout.signup_details);
    }

    public void display_content() throws JSONException {
        RecyclerView Recyclerview = findViewById(R.id.Recyclerview);
        JSONArray questions = new JSONArray(get_questions());
        JSONObject bl = questions.getJSONObject(2);
        String lic = bl.getString("question_id");
        List<MyItem> items = new ArrayList<>();

        // Iterate through the JSON array and create MyItem objects
        for (int i = 0; i < questions.length(); i++) {
            try {
                JSONObject questionObject = questions.getJSONObject(i);

                String id = questionObject.getString("user_id");
                String username = return_username(id);
                String questionText = questionObject.getString("question_text");
                int likeCount = questionObject.getInt("question_votes");
                int question_id = questionObject.getInt("question_id");

                MyItem item = new MyItem(username, questionText, likeCount, question_id);
                items.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Create and set the adapter
        MyAdapter adapter = new MyAdapter(items);
        Recyclerview.setAdapter(adapter);
        Recyclerview.setLayoutManager(new LinearLayoutManager(this));

    }

    public void display_question(View v){
        setContentView(R.layout.post_a_question);
    }
    public void post_a_question(View v) throws JSONException {
        EditText q =(EditText)findViewById(R.id.post_question_edittext);
        String question = q.getText().toString();

        String[] valid = {"false"}; //need to use this variable outside of "run" but java forces you to make it final so i made it an array
        client = new OkHttpClient();
        Log.i(TAG, "about to post question:"+valid_ID);
        String url = "https://lamp.ms.wits.ac.za/home/s2541305/post_question.php?user_id=" + valid_ID + "&question=" + question;
        Log.i(TAG, url);
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    valid[0] = responseBody;
                    Log.i(TAG, "HERE IT IS: " + responseBody);
                    // Parse the response to a boolean value
                    Log.i(TAG, "the value is currently:" + responseBody);


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
        set_homepage();
    }
    public String check_login_details(String username, String password){ //checks if details are correct
        Log.i(TAG, "function called sucessfully");
        String[] valid = {"false"}; //need to use this variable outside of "run" but java forces you to make it final so i made it an array
        client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2541305/checklogin.php?username=" + username + "&password=" + password;
        Log.i(TAG, url);
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    Log.i(TAG, "HERE IT IS: " + responseBody);
                    // Parse the response to a boolean value
                    Log.i(TAG, "the value is currently:" + responseBody);
                    valid[0] = responseBody; //updates array
                    Log.i(TAG, valid[0]);

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
        Log.i(TAG, "seems it worked?"+ valid[0]);
        return valid[0];
    }
    public void check_login_detail(View v) throws JSONException {

        EditText e = (EditText)findViewById(R.id.username_edittext);
        String username = e.getText().toString();

        EditText g = (EditText)findViewById(R.id.password_edittext);
        String password = g.getText().toString();
        String result = check_login_details(username, password);

        if(result.trim().equals("true")){
            valid_username= username.trim();

            String temp =return_ID(username).trim();
            int startIndex = 12;
            int endIndex = temp.length() - 2;

            valid_ID= temp.substring(startIndex, endIndex);
            Log.i(TAG, valid_ID+"==================");
            setContentView(R.layout.homepage);
            display_content();
             //this function adds all the questions to the scroll view on homepage
        }
        else{
            TextView error = findViewById(R.id.loginerror);
            error.setText("invalid username and password");
        }
    }
    public String check_signup_details(String username, String password){
        String[] valid = {"false"}; //need to use this variable outside of "run" but java forces you to make it final so i made it an array
        client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2541305/signup.php?username=" + username + "&password=" + password;
        Log.i(TAG, url);
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    Log.i(TAG, "HERE IT IS: " + responseBody);
                    // Parse the response to a boolean value
                    Log.i(TAG, "the value is currently:" + responseBody);
                    valid[0] = responseBody; //updates array
                    Log.i(TAG, valid[0]);

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
        Log.i(TAG, "seems it worked?"+ valid[0]);
        return valid[0];
    }
    public void check_signup_detail(View v) throws JSONException {
        TextView error1 = findViewById(R.id.username_error);
        TextView error2 = findViewById(R.id.password_error);
        TextView error3 = findViewById(R.id.username_exists_error);
        error1.setText("");
        error2.setText("");
        error3.setText("");

        EditText e = (EditText)findViewById(R.id.setusername_edittext);
        String username = e.getText().toString();

        EditText g = (EditText)findViewById(R.id.setpassword_edittext);
        String password = g.getText().toString();
        String result = "false";
        //check username and password else throw error
        if(!ValidString(username)){
            error1.setText("Invalid username");
            e.setText("");
            error3.setText("");
        }
        if(!ValidString(password)){
            error2.setText("Invalid password");
            g.setText("");
            error3.setText("");
        }
        if(ValidString(username)&&ValidString(password)){
            error3.setText("");
            result = check_signup_details(username, password);
        }
        if(ValidString(username)&&ValidString(password)&&result.trim().equals("true")){
            valid_username= username.trim();
            Log.i(TAG, "sign up set:"+ valid_username);
            String temp =return_ID(username).trim();
            int startIndex = 12;
            int endIndex = temp.length() - 2;

            valid_ID= temp.substring(startIndex, endIndex);
            Log.i(TAG, "sign up set:"+ valid_ID);
            setContentView(R.layout.homepage);
            display_content();

            error3.setText("");
        } else if (result.trim().equals("false")) {
            e.setText("");
            g.setText("");
            error3.setText("Username already exists");
        }
    }
    public void home_page(View v) throws JSONException {
        setContentView(R.layout.homepage);
        display_content();
        Log.i(TAG, "taking back to home page...");
    }
    public void guest_login(View v) throws JSONException {
        valid_username = "Guest";
        String temp =return_ID("Guest").trim();
        int startIndex = 12;
        int endIndex = temp.length() - 2;

        valid_ID= temp.substring(startIndex, endIndex);
        setContentView(R.layout.homepage);
        display_content();
        Log.i(TAG, "taking back to home page...");
    }
    public void set_homepage() throws JSONException {
        setContentView(R.layout.homepage);
        display_content();

    }

    public void display_comment_info(String Q_username, String question_id, String Q_likecount, String Q_text) throws JSONException {

        setContentView(R.layout.comment_section);
        TextView k = findViewById(R.id.question_username);
        TextView g = findViewById(R.id.question_likecount);
        TextView h = findViewById(R.id.question_question_card);

        k.setText(Q_username);
        g.setText(Q_likecount);
        h.setText(Q_text);

        RecyclerView Recyclerview = findViewById(R.id.commentview);
        String comments = get_comments(question_id);

        Log.i(TAG, comments+"heres the comments");
        if(comments.equals("{\"message\":\"No comments found.\"}")){
            setContentView(R.layout.blank_layout);
            TextView v = findViewById(R.id.question_id);
            v.setText(question_id);
        } else {
            JSONArray comment = new JSONArray(comments);
            List<commentitem> items = new ArrayList<>();
            // Iterate through the JSON array and create MyItem objects
            for (int i = 0; i < comment.length(); i++) {
                try {
                    JSONObject commentObject = comment.getJSONObject(i);

                    String id = commentObject.getString("user_id");
                    String username = return_username(id);
                    String commentText = commentObject.getString("comment_text");
                    int likeCount = commentObject.getInt("comment_votes");
                    int comment_id = commentObject.getInt("comment_id");

                    commentitem item = new commentitem(username, commentText, likeCount, comment_id);
                    items.add(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Create and set the adapter
            CommentAdapter adapter = new CommentAdapter(items);
            Recyclerview.setAdapter(adapter);
            Recyclerview.setLayoutManager(new LinearLayoutManager(this));
        }
    }
    public void display_comments(View view) throws JSONException {
        View parentLayout = (View) view.getParent();
        TextView e = parentLayout.findViewById(R.id.username);
        String Q_username = e.getText().toString();
        TextView f = parentLayout.findViewById(R.id.question_id);
        String question_id = f.getText().toString();
        TextView g = parentLayout.findViewById(R.id.likecount);
        String Q_likecount = g.getText().toString();
        TextView h = parentLayout.findViewById(R.id.question_card);
        String Q_text = h.getText().toString();
        display_comment_info(Q_username, question_id, Q_likecount, Q_text);
    }


    public void post_a_comment(View v) throws JSONException {
        TextView k = findViewById(R.id.idofquestion);
        String question_id = k.getText().toString();

        EditText q =(EditText)findViewById(R.id.post_question_edittext);
        String comment = q.getText().toString();

        String[] valid = {"false"}; //need to use this variable outside of "run" but java forces you to make it final so i made it an array
        client = new OkHttpClient();
        Log.i(TAG, "about to post comment:"+valid_ID);
        String url = "https://lamp.ms.wits.ac.za/home/s2541305/post_comment.php?user_id=" + valid_ID + "&questionid="+ question_id + "&comment=" + comment;
        Log.i(TAG, url);
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    valid[0] = responseBody;
                    Log.i(TAG, "HERE IT IS: " + responseBody);
                    // Parse the response to a boolean value
                    Log.i(TAG, "the value is currently:" + responseBody);


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
        set_homepage();
    }
    public void comment_layout(View v){
        View parentLayout = (View) v.getParent();
        TextView f = parentLayout.findViewById(R.id.question_id);
        String question_id = f.getText().toString();
        setContentView(R.layout.post_a_comment);
        TextView k = findViewById(R.id.idofquestion);
        k.setText(question_id);
    }

    public void like_question(View v){
        String tag = (String) v.getTag();
        View parentLayout = (View) v.getParent();
        TextView f = parentLayout.findViewById(R.id.question_id);
        ImageButton k = parentLayout.findViewById(R.id.like_button);
        String question_id = f.getText().toString();
        TextView likes = parentLayout.findViewById(R.id.likecount);
        String like_count = likes.getText().toString();

        String temp = return_ID(valid_username);
        int startIndex = 12;
        int endIndex = temp.length() - 2;

        String id= temp.substring(startIndex, endIndex);
        String liked = like_question_request(question_id, id).trim();
        Log.i(TAG, "result of like request:"+ liked);
        if(tag.trim().equals("empty")){
            if(liked.equals("upvoted")){
                v.setTag("full");
                k.setImageResource(R.drawable.filled_heart);
                likes.setText(String.valueOf(Integer.parseInt(like_count)+1));
            } else if (liked.equals("vote_removed")) {
                likes.setText(String.valueOf(Integer.parseInt(like_count)-1));
            }
        } else if(tag.trim().equals("full")){
            if(liked.equals("vote_removed")){
                v.setTag("empty");
                k.setImageResource(R.drawable.like_icon);
                likes.setText(String.valueOf(Integer.parseInt(like_count)-1));
            }
        }
    }
    public void like_comment(View v){
        String tag = (String) v.getTag();
        View parentLayout = (View) v.getParent();
        TextView f = parentLayout.findViewById(R.id.comment_id);
        ImageButton k = parentLayout.findViewById(R.id.commentor_like_button);
        String comment_id = f.getText().toString();
        TextView likes = parentLayout.findViewById(R.id.commentor_likecount);
        String like_count = likes.getText().toString();

        String temp = return_ID(valid_username);
        int startIndex = 12;
        int endIndex = temp.length() - 2;

        String id= temp.substring(startIndex, endIndex);
        String liked = like_comment_request(comment_id, id).trim();
        if(tag.trim().equals("empty")){
            if(liked.equals("upvoted")){
                v.setTag("full");
                k.setImageResource(R.drawable.filled_heart);
                likes.setText(String.valueOf(Integer.parseInt(like_count)+1));
            } else if (liked.equals("vote_removed")) {
                likes.setText(String.valueOf(Integer.parseInt(like_count)-1));
            }
        } else if(tag.trim().equals("full")){
            if(liked.equals("vote_removed")){
                v.setTag("empty");
                k.setImageResource(R.drawable.like_icon);
                likes.setText(String.valueOf(Integer.parseInt(like_count)-1));
            }
        }
    }
}