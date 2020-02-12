package com.example.android.retrofitexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.text_view_result);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPosts();
        getComments();
        //createPost();
    }

    private void getPosts(){
        Map<String, String> parametros =  new HashMap<>();
        parametros.put("userId", "1");
        parametros.put("_sort", "id");
        parametros.put("_order", "desc");


        Call<List<Post>> call = jsonPlaceHolderApi.getPost(new Integer[]{4,8,9}, "id", "desc");
        Call<List<Post>> call2 = jsonPlaceHolderApi.getPosts(parametros);//Pode-se passar null.

        //.enqueue faz uma requisição de execução assíncrona
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()) {
                    textResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> postsResponse = response.body();
                for(Post post : postsResponse){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textResult.append(content);
                }
            }

            //Caso a comunicação falhar, ou recebermos uma resposta de falha.
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    private void getComments(){

        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(3);
        Call<List<Comment>> call2 = jsonPlaceHolderApi.getComments("comments?postId=2");

        //.enqueue faz uma requisição de execução assíncrona
        call2.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()) {
                    textResult.setText("Code: " + response.code());
                    return;
                }

                List<Comment> commentResponse = response.body();
                for(Comment comment : commentResponse){
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    textResult.append(content);
                }
            }

            //Caso a comunicação falhar, ou recebermos uma resposta de falha.
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    private void createPost(){
        Post post = new Post(23, "New title", "New text");

        Call<Post> call = jsonPlaceHolderApi.createPost(post);
        Call<Post> call2 = jsonPlaceHolderApi.createPost(24, "New title 2", "New text 2");

        //Pode-se também usar Map:
        Map<String, String> fields =  new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New title 3");

        Call<Post> call3 = jsonPlaceHolderApi.createPost(fields);


        call3.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()) {
                    textResult.setText("Code: " + response.code());
                    return;
                }
                Post postResponse = response.body();

                String content = "";

                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }
}
